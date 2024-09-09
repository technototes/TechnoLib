// This is a bun-specific replacement for the run-script-os NodeJS thing
// It takes a script, and invokes the "script:os" command
// if the script:win/mac/lin command doesn't exist, it falls back to the script:def command
import path from 'path';
import fs from 'fs/promises';
import os from 'os';
import { chkObjectOf, hasFieldType, hasStrField, isString } from '@freik/typechk';
import Bun from 'bun';

const osmap = new Map<string, string>([
  ['win32', 'win'],
  ['darwin', 'mac'],
  ['linux', 'lin'],
]);

async function main() {
  // argv[1] is this script's name, so find the package.json file from there
  const pkg = path.resolve(path.join(path.dirname(Bun.argv[1]), '..', 'package.json'));
  const args = Bun.argv.slice(2);
  const pkgj = JSON.parse(await fs.readFile(pkg, 'utf8'));
  if (!hasFieldType(pkgj, 'scripts', chkObjectOf(isString))) {
    throw new Error('No scripts field in package.json');
  }
  const scriptName = args[0];
  const suffix = osmap.get(os.platform()) || 'def';
  const script = `${scriptName}:${suffix}`;
  const scriptdef = `${scriptName}:def`;
  const theScript = hasStrField(pkgj.scripts, script)
    ? script
    : hasStrField(pkgj.scripts, scriptdef)
      ? scriptdef
      : undefined;
  if (!isString(theScript)) {
    throw new Error(`No script found for ${scriptName} for ${os.platform()}`);
  }
  const cmds = args
    .slice(1)
    .map((v) => Bun.$.escape(v))
    .join(' ');
  await Bun.$`bun run ${theScript} ${{ raw: cmds }}`;
}

main().catch((err) => console.error(err));
