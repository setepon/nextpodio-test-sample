Prerequisite
1. Install JAVA7 @ https://docs.oracle.com/javase/7/docs/webnotes/install/
(check java installation by running "java -version")
2. Install Maven 3.2.3 @ http://maven.apache.org/download.cgi
(check maven installation by running "mvn -version")
3. Configure the test-config.properties @ nextpodio-test-sample/src/test/resources

#context root URL of start point
contextRoot=https://nextpodio.dk/login

#Add the username of the test user to the following key
userName=professor@zgvkblzwmnxwrnruudbf.com
#Add password value for the test user
password=professor_$ecur3Pwd


Where are the description of test case
Please find the sheet @ nextpodio-test-sample/test_case.xlst

How to run test case (run on Chrome)
1. Run "mvn integration-test" under nextpodio-test-sample folder
2. Open report with browser @ nextpodio-test-sample/target/failsafe-reports/html/index.html

FAQ
1. Is it possible to run test case on all (WIN, LINUX, MAC) platforms?
Answer: It only could be launched on MAC and WIN simply because only chrome driver of MAC and Windows are included in the project

2. On Mac, you need to copy the chromedriver from nextpodio-test-sample/src/test/resources/webdriver to nextpodio-test-sample/target/test-classes/webdriver
Change the permission after copy. It is due to maven resource plugin known issue @ http://jira.codehaus.org/browse/MRESOURCES-132

Possible improvement (not implemented due to time pressure)
1. Setup a grid (hub and node)
2. All test class extends from BaseGuiTest and use broswerConfig.json to configure the browsers to run
3. Execute test cases in parallel 