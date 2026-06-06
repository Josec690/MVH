param(
    [string]$MysqlUser = 'root',
    [string]$MysqlDb = 'museu_virtual_hardware',
    [string]$ProjectDir = (Get-Location)
)

Write-Host "Running mysql-setup.sql (you will be prompted for MySQL password)..."
& $env:ComSpec /c "mysql -u $MysqlUser -p < \"$ProjectDir\mysql-setup.sql\""

Write-Host "Importing import.sql into database $MysqlDb..."
& $env:ComSpec /c "mysql -u $MysqlUser -p $MysqlDb < \"$ProjectDir\src\main\resources\import.sql\""

Write-Host "Done."
