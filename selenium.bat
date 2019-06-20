set ProjectPath=C:\Users\rgajul\eclipse-workspace\IManagement
echo %ProjectPath%
set classpath=%ProjectPath%\bin;%ProjectPath%\Lib\*
echo %classpath%
java org.testng.TestNG %ProjectPath%\testsuite.xml