package com.agileengine.smartxml;

import com.agileengine.smartxml.comparison.DefaultComparisionStrategy;
import com.agileengine.smartxml.comparison.ElementComparisonStrategy;
import org.junit.Test;
import java.io.File;
import java.nio.file.Paths;
import static org.junit.Assert.*;


public class SmartXmlAnalyzerTest {

    private File originFile;
    private final String originElementId = "make-everything-ok-button";
    private ElementComparisonStrategy defaultComparisionStrategy = new DefaultComparisionStrategy();

    public SmartXmlAnalyzerTest() {
        var originFilePath = Paths.get("src", "test", "resources", "xml", "sample-0-origin.html");
        originFile = originFilePath.toFile();
    }

    @Test
    public void testEvilGemini() {
        AssertMatchedElement(
                "sample-1-evil-gemini.html",
                "/html[1]/body[1]/div[1]/div[1]/div[3]/div[1]/div[1]/div[2]/a[1]");
    }

    @Test
    public void testContainerAndClone() {
        AssertMatchedElement(
                "sample-2-container-and-clone.html",
                "/html[1]/body[1]/div[1]/div[1]/div[3]/div[1]/div[1]/div[2]/div[1]/a[1]");
    }


    @Test
    public void testTheEscape() {
        AssertMatchedElement(
                "sample-3-the-escape.html",
                "/html[1]/body[1]/div[1]/div[1]/div[3]/div[1]/div[1]/div[2]/a[1]");
    }

    @Test
    public void testTheMash() {
        AssertMatchedElement(
                "sample-4-the-mash.html",
                "/html[1]/body[1]/div[1]/div[1]/div[3]/div[1]/div[1]/div[3]/a[1]");
    }

    private void AssertMatchedElement(String fileName, String resultPath){
        var comparisonFilePath = Paths.get("src", "test", "resources", "xml", fileName);
        var comparisonFile = comparisonFilePath.toFile();
        var analyzer = SmartXmlAnalyzer.createAnalyzer(originFile, comparisonFile, defaultComparisionStrategy);
        var matchedElement = analyzer.GetMatchedElement(originElementId);
        assertTrue(matchedElement.isPresent());
        var matchedElementPath = matchedElement.get().getPath();
        assertEquals(resultPath, matchedElementPath);
    }

}
