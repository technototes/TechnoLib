@cd docs
@rem Get rid of cr-lf's, because it's annoying
@for /D /R %%D in (*) do dos2unix "%%~D\*.js" "%%~D\*.html" "%%~D\*.css"