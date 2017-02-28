# MIW_Automation_Exercise

Here are the 3 Selenium test cases.

They are jUnit tests written in Java using the Selenium Java 3.1.0 library.

I prefer to use TestNG, it is what I am more familiar with. It would allow the use of @DataProviders to automatically run both pass/fail iteration of the tests, and have tests depend on other tests. However, since I didn't have TestNG setup and configured on my personal computer, and in the interest of time, I used jUnit. The unfortunate result to me using jUnit is that I have never setup parameterized tests so all pass/fail iteration must be performed manually. I also realized while writing up this readme that I left out some useful validation point in the searchRichieBros() test i.e. verifying the actual "age" of the excavator in the search results.


####System Setup and Requirements
######Requirements
1. IntelliJ IDEA CE 2016.3.4
2. Java jdk1.8.0_121
3. Selenium Java 3.1.0
4. Chromedriver win32

######Setup
An assumption is being made that the user understands how to perform the following steps.

1. Download and install IntelliJ
2. Download and install Java JDK
3. Create the environment variable JAVA_HOME pointing to jdk1.8.0_121
4. Add the Selenium .jar file to the Project dependencies
5. Checkout the MIW_Automation_Exercise from GitHub (https://github.com/jclaudon/MIW_Automation_Exercise)

######Running the Tests
1. Open IntelliJ
2. Navigate and Open SeleniumTest.java
3. Click the Run arrow next to the desired @Test

Note: All tests are currently configured to be valid "pass" tests

####Test Plan
######openGoogle()
openGoogle() test is designed to open the browser and navigate to www.google.com.

The following validation point are used to verify a successful test
- Page loads in less than 10 seconds
- The expected title of the page is "Google"
- I chose to verify 3 key elements on the page.
    - The "Google" header id = lga
    - The "Search" text box id = sbtc
    - The "Search" button name = btnK

To cause openGoogle() test to fail:
- Modify the url to point to something other than www.google.com
- Modify one of the element id's that are being verified.

######searchGoogle()
searchGoogle() test is designed to open the browser, navigate to www.google.com, and perform and validate a search

The following validation point are used to verify a successful test:
- Page loads in less than 10 seconds
- The expected title of the page is "Google"
- I chose to verify 3 key elements on the page.
    - The "Google" header id = lga
    - The "Search" text box id = sbtc
    - The "Search" button name = btnK
- The first element of the search result matches the expected result

To cause the searchGoogle() test to fail:
- Modify the url to point to something other than www.google.com
- Modify the searchString to something other than "mobile integration workgroup"

######searchRichieBros()
searchRichieBros() test is designed to open the browser, navigate to www.rbauction.com, search for an excavator of a particular brand and age range, print the "details" of the first item in the search results.

The following validation point are used to verify a successful test:
- Page loads in less than 10 seconds
- The expected title of the page is "Google"
- I chose to verify 3 key elements on the page.
    - The "Google" header id = lga
    - The "Search" text box id = sbtc
    - The "Search" button name = btnK
- The first element of the search result matches the expected result

To cause the searchRichieBros() test to fail:
- Modify the url to point to something other than https://www.rbauction.com
- Modify the select element to something other than "Construction"
- Modify the select element to something other than "Excavators"
- Modify the string to something other than "CATERPILLAR"

####Code and Framework
My framework is a very simple Java/Selenium based framework. I created jUnit tests for the sake of time. However, in hind sight, I should have taken the time to install and configure TestNG, as I now realize the limitations of jUnit. The SeleniumTests.java class extends the BaseSelenium.java class, where I try to create reusable methods that could be shared/extended by other classes if needed, and it is better for readability purposes.

####GitHub Location
https://github.com/jclaudon/MIW_Automation_Exercise