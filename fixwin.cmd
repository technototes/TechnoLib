pushd docs
for /R %%D in (COPYRIGHT LICENSE element-list *.html *.js *.css) do dos2unix "%%~D"
popd