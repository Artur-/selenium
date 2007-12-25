/*
 * Copyright 2007 ThoughtWorks, Inc
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.thoughtworks.webdriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

import java.awt.Dimension;
import java.awt.Point;
import java.util.List;

/**
 * Test case for browsers that support using Javascript
 */
public abstract class JavascriptEnabledDriverTest extends BasicDriverTestCase {
    public void testDocumentShouldReflectLatestTitle() throws Exception {
        driver.get(javascriptPage);

        assertThat(driver.getTitle(), equalTo("Testing Javascript"));
        driver.findElement(By.linkText("Change the page title!")).click();
        assertThat(driver.getTitle(), equalTo("Changed"));

        String titleViaXPath = driver.findElement(By.xpath("/html/head/title")).getText();
        assertThat(titleViaXPath, equalTo("Changed"));
    }

    public void testDocumentShouldReflectLatestDom() throws Exception {
        driver.get(javascriptPage);
        String currentText = driver.findElement(By.xpath("//div[@id='dynamo']")).getText();
        assertThat(currentText, equalTo("What's for dinner?"));

        WebElement webElement = driver.findElement(By.linkText("Update a div"));
        webElement.click();

        String newText = driver.findElement(By.xpath("//div[@id='dynamo']")).getText();
        assertThat(newText, equalTo("Fish and chips!"));
    }

    public void testWillSimulateAKeyUpWhenEnteringTextIntoInputElements() {
        driver.get(javascriptPage);
        WebElement element = driver.findElement(By.id("keyUp"));
        element.setValue("I like cheese");

        WebElement result = driver.findElement(By.id("result"));
        assertThat(result.getText(), equalTo("I like cheese"));
    }

    public void testWillSimulateAKeyDownWhenEnteringTextIntoInputElements() {
        driver.get(javascriptPage);
        WebElement element = driver.findElement(By.id("keyDown"));
        element.setValue("I like cheese");

        WebElement result = driver.findElement(By.id("result"));
        // Because the key down gets the result before the input element is
        // filled, we're a letter short here
        assertThat(result.getText(), equalTo("I like chees"));
    }

    public void testWillSimulateAKeyPressWhenEnteringTextIntoInputElements() {
        driver.get(javascriptPage);
        WebElement element = driver.findElement(By.id("keyPress"));
        element.setValue("I like cheese");

        WebElement result = driver.findElement(By.id("result"));
        // Because the key down gets the result before the input element is
        // filled, we're a letter short here
        assertThat(result.getText(), equalTo("I like chees"));
    }

    public void testWillSimulateAKeyUpWhenEnteringTextIntoTextAreas() {
        driver.get(javascriptPage);
        WebElement element = driver.findElement(By.id("keyUpArea"));
        element.setValue("I like cheese");

        WebElement result = driver.findElement(By.id("result"));
        assertThat(result.getText(), equalTo("I like cheese"));
    }

    public void testWillSimulateAKeyDownWhenEnteringTextIntoTextAreas() {
        driver.get(javascriptPage);
        WebElement element = driver.findElement(By.id("keyDownArea"));
        element.setValue("I like cheese");

        WebElement result = driver.findElement(By.id("result"));
        // Because the key down gets the result before the input element is
        // filled, we're a letter short here
        assertThat(result.getText(), equalTo("I like chees"));
    }

    public void testWillSimulateAKeyPressWhenEnteringTextIntoTextAreas() {
        driver.get(javascriptPage);
        WebElement element = driver.findElement(By.id("keyPressArea"));
        element.setValue("I like cheese");

        WebElement result = driver.findElement(By.id("result"));
        // Because the key down gets the result before the input element is
        // filled, we're a letter short here
        assertThat(result.getText(), equalTo("I like chees"));
    }

    public void testsShouldIssueMouseDownEvents() {
        driver.get(javascriptPage);
        driver.findElement(By.id("mousedown")).click();

        String result = driver.findElement(By.id("result")).getText();
        assertThat(result, equalTo("mouse down"));
    }

    public void testShouldIssueClickEvents() {
        driver.get(javascriptPage);
        driver.findElement(By.id("mouseclick")).click();

        String result = driver.findElement(By.id("result")).getText();
        assertThat(result, equalTo("mouse click"));
    }

    public void testShouldIssueMouseUpEvents() {
        driver.get(javascriptPage);
        driver.findElement(By.xpath("//div[@id='mouseup']")).click();

        String result = driver.findElement(By.id("result")).getText();
        assertThat(result, equalTo("mouse up"));
    }

    public void testMouseEventsShouldBubbleUpToContainingElements() {
        driver.get(javascriptPage);
        driver.findElement(By.xpath("//p[@id='child']")).click();

        String result = driver.findElement(By.id("result")).getText();
        assertThat(result, equalTo("mouse down"));
    }

    public void testShouldEmitOnChangeEventsWhenSelectingElements() {
        driver.get(javascriptPage);
        WebElement select = driver.findElement(By.id("selector"));
        List<WebElement> allOptions = select.getChildrenOfType("option");

        String initialTextValue = driver.findElement(By.id("result")).getText();

        WebElement foo = allOptions.get(0);
        WebElement bar = allOptions.get(1);

        foo.setSelected();
        assertThat(driver.findElement(By.id("result")).getText(), equalTo(initialTextValue));
        bar.setSelected();
        assertThat(driver.findElement(By.id("result")).getText(), equalTo("bar"));
    }

    public void testShouldEmitOnChangeEventsWhenChnagingTheStateOfACheckbox() {
        driver.get(javascriptPage);
        WebElement checkbox = driver.findElement(By.id("checkbox"));

        checkbox.setSelected();
        assertThat(driver.findElement(By.id("result")).getText(), equalTo("checkbox thing"));
    }

//    public void testShouldAllowTheUserToOkayConfirmAlerts() {
//		driver.get(alertPage);
//		driver.findElement(By.id("confirm").click();
//		driver.switchTo().alert().accept();
//		assertEquals("Hello WebDriver", driver.getTitle());
//	}
//
//	public void testShouldAllowUserToDismissAlerts() {
//		driver.get(alertPage);
//		driver.findElement(By.id("confirm").click();
//
//		driver.switchTo().alert().dimiss();
//		assertEquals("Testing Alerts", driver.getTitle());
//	}
//
//	public void testShouldBeAbleToGetTheTextOfAnAlert() {
//		driver.get(alertPage);
//		driver.findElement(By.id("confirm").click();
//
//		String alertText = driver.switchTo().alert().getText();
//		assertEquals("Are you sure?", alertText);
//	}
//
//	public void testShouldThrowAnExceptionIfAnAlertIsBeingDisplayedAndTheUserAttemptsToCarryOnRegardless() {
//		driver.get(alertPage);
//		driver.findElement(By.id("confirm").click();
//
//		try {
//			driver.get(simpleTestPage);
//			fail("Expected the alert not to allow further progress");
//		} catch (UnhandledAlertException e) {
//			// This is good
//		}
//	}

    public void testShouldAllowTheUserToTellIfAnElementIsDisplayedOrNot() {
        driver.get(javascriptPage);

        assertThat(((RenderedWebElement) driver.findElement(By.id("displayed"))).isDisplayed(), is(true));
        assertThat(((RenderedWebElement) driver.findElement(By.id("none"))).isDisplayed(), is(false));
        assertThat(((RenderedWebElement) driver.findElement(By.id("hidden"))).isDisplayed(), is(false));
    }

    public void testShouldWaitForLoadsToCompleteAfterJavascriptCausesANewPageToLoad() {
        driver.get(formPage);

        driver.findElement(By.id("changeme")).setSelected();

        assertThat(driver.getTitle(), equalTo("Page3"));
    }

    public void testShouldBeAbleToDetermineTheLocationOfAnElement() {
        driver.get(xhtmlTestPage);

        RenderedWebElement element = (RenderedWebElement) driver.findElement(By.id("username"));
        Point location = element.getLocation();

        assertThat(location.getX() > 0, is(true));
        assertThat(location.getY() > 0, is(true));
    }

    public void testShouldBeAbleToDetermineTheSizeOfAnElement() {
        driver.get(xhtmlTestPage);

        RenderedWebElement element = (RenderedWebElement) driver.findElement(By.id("username"));
        Dimension size = element.getSize();

        assertThat(size.getWidth() > 0, is(true));
        assertThat(size.getHeight() > 0, is(true));
    }

    public void testShouldFireOnChangeEventWhenSettingAnElementsValue() {
      driver.get(javascriptPage);
      driver.findElement(By.id("change")).setValue("foo");
      String result = driver.findElement(By.id("result")).getText();

      assertThat(result, equalTo("change"));
    }

    public void testShouldFireFocusKeyBlurAndChangeEventsInTheRightOrder() {
        driver.get(javascriptPage);

        driver.findElement(By.id("theworks")).setValue("a");
        String result = driver.findElement(By.id("result")).getText();

        assertThat(result.trim(), equalTo("focus keydown keypress keyup blur change"));
    }
}
