pushd docs
@rem The start /B makes this run much faster, but the output is garbled. Oh well...
for /R %%D in (COPYRIGHT LICENSE element-list *.html *.js *.css) do start /B dos2unix "%%~D"
popd
@rem do a 'yarn format' when this is done
@rem you could do it here, but that's a really nasty race condition.
@rem Maybe this should be a node script instead...
