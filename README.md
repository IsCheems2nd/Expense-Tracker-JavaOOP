# Expense-Tracker-JavaOOP
Final Project for our JavaOOP class
Build steps:
Install maven installer or through scoop/choco package manager, we recommend using choco. If it’s not available, run Powershell as admin and run
Get-ExecutionPolicy. If it returns Restricted, then run Set-ExecutionPolicy AllSigned
Then, run Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))

You should have chocolatey installed at this point, then run choco install maven for Apache Maven. Finally, on the root project directory, run mvn clean install exec:java