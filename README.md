# UI Automation Framework – Selenium

## **Summary:**

The UI Automation framework has been developed using Java, Selenium-Java, TestNg, Maven and Allure-Reporting tool.

The Steps below contains framework installation, features, its structure ,Allure reporting and a sample video on how to install the framework and write the page objects.

**Pre-requisites:**

Make sure the below software is installed in your machine before proceeding.

- Java jdk 1.8 ([Link](https://www.oracle.com/technetwork/java/javase/downloads/index.html))
- Maven 3.x ([Link](https://maven.apache.org/download.cgi?Preferred=ftp://mirror.reverse.net/pub/apache/))
- Allure Reporting ([Link](https://github.com/allure-framework/allure2/blob/master/README.md))

## **Installation:**

- Clone/Download the repo from [https://gitlab-sjc.cisco.com/cx-india-qa/ui-automation-framework.git](https://gitlab-sjc.cisco.com/cx-india-qa/ui-automation-framework.git)
- Switch to framework branch.
- Run the Maven command mvn clean verify. (It will download all the dependency and run a few sample tests in the chrome browser).
- To see the report, open the command prompt in the root directory of the project and type the command **allure serve**. The report will automatically open in the web browser.
- To start writing the testcase, replace the files under com/cisco/**tests** and com/cisco/**pages** (Except Base page) and **test-data** folder with the one specific to the application.

## **Structure:**

![https://wiki.cisco.com/download/attachments/378216765/Structure.png?version=1&modificationDate=1563885967000&api=v2](https://wiki.cisco.com/download/attachments/378216765/Structure.png?version=1&modificationDate=1563885967000&api=v2)

## **Features:**

### **a. Parallel Execution:**

The Framework supports running the testcases in parallel. For Instance, there are 100 testcases in a suite, and we want to run the 100 testcases in 4 threads, then those 100 testcases will be distributed among the 4 threads as per the availability.

It supports parallel execution by **methods**.

Below we can see the configuration in the failsafe plugin in the pom.xml.

![https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-21-50.png?version=1&modificationDate=1563886310000&api=v2](https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-21-50.png?version=1&modificationDate=1563886310000&api=v2)

By default, the threads value is **1**. But we can change that with the maven switch.

Example: If we want to run three threads in parallel then command should be:

![https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-22-39.png?version=1&modificationDate=1563886360000&api=v2](https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-22-39.png?version=1&modificationDate=1563886360000&api=v2)

Similarly, we can give any value to the threadCount property

### **b. Cross Browser:**

The Framework supports running the testcases in different browsers. The default browser is chrome and we don’t have to explicitly specify this in the switch.

But to run the testcase in Firefox, IE or EDGE, the following command should be given.

![https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-24-9.png?version=1&modificationDate=1563886449000&api=v2](https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-24-9.png?version=1&modificationDate=1563886449000&api=v2)

**Note:** The maven command can contain any number of switches. is valid command.

![https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-24-22.png?version=1&modificationDate=1563886462000&api=v2](https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-24-22.png?version=1&modificationDate=1563886462000&api=v2)

The browser used can be seen in the Allure report under the environment section.

![https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-24-43.png?version=1&modificationDate=1563886484000&api=v2](https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-24-43.png?version=1&modificationDate=1563886484000&api=v2)

Note: Only One browser is possible at a time. For running multiple browsers at a time, Selenium grid can be used.

### **c. Headless Run:**

**Headless Browser:** Headless browser is a browser capable of working on a device without a graphical user interface. Browsers like chrome, Firefox can run in headless mode.

For Running the testcases in Headless mode we must specify the headless switch in the maven command.

![https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-25-56.png?version=1&modificationDate=1563886556000&api=v2](https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-25-56.png?version=1&modificationDate=1563886556000&api=v2)

By default, headless switch is false.

### **d. Group Run:**

The framework supports running the testcases in groups. For instance, if we want to run the sanity testcases then,

First add groups={“sanity”} in @Test annotation.

![https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-26-57.png?version=1&modificationDate=1563886617000&api=v2](https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-26-57.png?version=1&modificationDate=1563886617000&api=v2)

Second provide the maven switch -Dgroups=sanity while running

![https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-27-15.png?version=1&modificationDate=1563886636000&api=v2](https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-27-15.png?version=1&modificationDate=1563886636000&api=v2)

If we want to run multiple groups, then provide the group name with comma separated values.

![https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-27-25.png?version=1&modificationDate=1563886645000&api=v2](https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-27-25.png?version=1&modificationDate=1563886645000&api=v2)

The same can be seen in the allure report under the Environment section.

![https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-27-39.png?version=1&modificationDate=1563886660000&api=v2](https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-27-39.png?version=1&modificationDate=1563886660000&api=v2)

One more important group feature is, every test method is annotated with groups={“regression”} during the run time. So, if we want to run the regression suite then give following maven command.

![https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-28-3.png?version=1&modificationDate=1563886684000&api=v2](https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-28-3.png?version=1&modificationDate=1563886684000&api=v2)

Same can be seen in the report,

![https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-28-12.png?version=1&modificationDate=1563886692000&api=v2](https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-28-12.png?version=1&modificationDate=1563886692000&api=v2)

### **e. Filtering the Testcase**

The framework supports filtering of the testcases based on the parameter passed. Following are the different ways we can filter the testcases.

For example, we have two Test Class HomePageIT and SearchPageTests and both contains the following methods.

![https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-28-56.png?version=1&modificationDate=1563886736000&api=v2](https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-28-56.png?version=1&modificationDate=1563886736000&api=v2)

![https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-29-11.png?version=1&modificationDate=1563886751000&api=v2](https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-29-11.png?version=1&modificationDate=1563886751000&api=v2)

- To run a single test class

![https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-29-41.png?version=1&modificationDate=1563886781000&api=v2](https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-29-41.png?version=1&modificationDate=1563886781000&api=v2)

- To run multiple test classes

![https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-29-51.png?version=1&modificationDate=1563886791000&api=v2](https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-29-51.png?version=1&modificationDate=1563886791000&api=v2)

- To provide regular expression

![https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-30-11.png?version=1&modificationDate=1563886811000&api=v2](https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-30-11.png?version=1&modificationDate=1563886811000&api=v2)

- To run a single Test method

![https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-30-26.png?version=1&modificationDate=1563886826000&api=v2](https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-30-26.png?version=1&modificationDate=1563886826000&api=v2)

Refer to the below link for more options:

[https://maven.apache.org/surefire/maven-failsafe-plugin/examples/single-test.html](https://maven.apache.org/surefire/maven-failsafe-plugin/examples/single-test.html)

### **f. Retry Failed Testcases**

The framework provides the feature to rerun the failed testcases. The number of times we retry depends on the count we provide. By default, the count is zero.

To rerun the Failed testcases two times run the following maven command.

![https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-31-15.png?version=1&modificationDate=1563886875000&api=v2](https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-31-15.png?version=1&modificationDate=1563886875000&api=v2)

We can see in the allure report that it was rerun twice after failure.

![https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-31-28.png?version=1&modificationDate=1563886888000&api=v2](https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-31-28.png?version=1&modificationDate=1563886888000&api=v2)

**Note: The report shows the retried attempt as skipped because this is a testing feature.**

### **g.  Reuse the Browser Instance:**

To open the browser instance for each test method eats up enough time. Therefore, to cut down that time, the framework supports reusing the browser instance for test method.

For instance, if we are running two threads for our testcases, then the browser will open only twice during the run.

After each run the browser cookies get deleted, to make it clean for the next test run.

But, there are exceptions, some applications stored the cookies in the server. Therefore, even if we delete the cookies after each test run, the browser will not be completely clean for the next.

For e.g. The CSC application uses server sides cookies, so if we login in one of our test methods, it remains logged in even if we delete the cookies.

So, for this instance we must use the following maven command.

![https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-32-11.png?version=1&modificationDate=1563886931000&api=v2](https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-32-11.png?version=1&modificationDate=1563886931000&api=v2)

It will open a fresh browser for each test methods.

### **h. Auto download driver Binaries:**

The framework uses “driver-binary-downloader-maven-plugin” plugin which automatically downloads the driver binaries.

![https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-32-40.png?version=1&modificationDate=1563886960000&api=v2](https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-32-40.png?version=1&modificationDate=1563886960000&api=v2)

## **Allure Reporting:**

Allure is based on standard TestNg results output but adds some supplementary data. Any report is generated in two steps. During **test execution** (first step), a small library called **adapter** attached to the testing framework saves information about executed tests to XML files

During **report generation** (second step), the XML files are transformed to a HTML report. This can be done with a command line tool, a plugin for CI or a build tool.

- **Add a test step**.

![https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-33-9.png?version=1&modificationDate=1563886990000&api=v2](https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-33-9.png?version=1&modificationDate=1563886990000&api=v2)

Or

![https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-33-17.png?version=1&modificationDate=1563886997000&api=v2](https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-33-17.png?version=1&modificationDate=1563886997000&api=v2)

It can be seen in the report as

![https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-33-39.png?version=1&modificationDate=1563887020000&api=v2](https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-33-39.png?version=1&modificationDate=1563887020000&api=v2)

- **Name a Testcase**: To name a testcase, provide description in the @Test method

![https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-34-10.png?version=1&modificationDate=1563887050000&api=v2](https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-34-10.png?version=1&modificationDate=1563887050000&api=v2)

It can be seen in the report as

![https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-34-26.png?version=1&modificationDate=1563887066000&api=v2](https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-34-26.png?version=1&modificationDate=1563887066000&api=v2)

- **Add description:**

![https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-34-58.png?version=1&modificationDate=1563887098000&api=v2](https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-34-58.png?version=1&modificationDate=1563887098000&api=v2)

It can be seen in the report as:

![https://wiki.cisco.com/download/thumbnails/378216765/image2019-7-23_18-35-18.png?version=1&modificationDate=1563887118000&api=v2](https://wiki.cisco.com/download/thumbnails/378216765/image2019-7-23_18-35-18.png?version=1&modificationDate=1563887118000&api=v2)

- **Add a feature**

![https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-35-40.png?version=1&modificationDate=1563887141000&api=v2](https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-35-40.png?version=1&modificationDate=1563887141000&api=v2)

![https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-36-1.png?version=1&modificationDate=1563887161000&api=v2](https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-36-1.png?version=1&modificationDate=1563887161000&api=v2)

- **Command to Serve report:**

![https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-36-14.png?version=1&modificationDate=1563887174000&api=v2](https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-36-14.png?version=1&modificationDate=1563887174000&api=v2)

It will automatically generate the report in the temp directory and opens the report in the default browser.

- **Command to generate the report in the current directory.**

![https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-36-27.png?version=1&modificationDate=1563887187000&api=v2](https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-36-27.png?version=1&modificationDate=1563887187000&api=v2)

It will create an allure-report folder in the project base directory.

## **Sample Report:**

![https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-36-46.png?version=1&modificationDate=1563887207000&api=v2](https://wiki.cisco.com/download/attachments/378216765/image2019-7-23_18-36-46.png?version=1&modificationDate=1563887207000&api=v2)

**Video:**

**[Video Link](https://cisco.webex.com/cisco/ldr.php?RCID=86bb2191136677a5006a8bf62a518f09)**

**Password- rGss3cyM**

**Git Basics:**

[https://wiki.cisco.com/plugins/servlet/view-file-macro/placeholder?type=PowerPoint+Presentation&name=Git+Workflow.pptx&attachmentId=473508747&version=1&mimeType=application%2Fvnd.openxmlformats-officedocument.presentationml.presentation&height=250](https://wiki.cisco.com/plugins/servlet/view-file-macro/placeholder?type=PowerPoint+Presentation&name=Git+Workflow.pptx&attachmentId=473508747&version=1&mimeType=application%2Fvnd.openxmlformats-officedocument.presentationml.presentation&height=250)
