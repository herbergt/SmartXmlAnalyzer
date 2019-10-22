package com.agileengine.smartxml;

import com.agileengine.smartxml.comparison.ElementComparisonStrategy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class SmartXmlAnalyzer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SmartXmlAnalyzer.class);
    private File originFile;
    private File comparisonFile;
    private ElementComparisonStrategy comparisionStrategy;

    private SmartXmlAnalyzer() {
    }

    public static SmartXmlAnalyzer createAnalyzer(
            File originFile,
            File fileToCompare,
            ElementComparisonStrategy comparisionStrategy) {
        var analyzer = new SmartXmlAnalyzer();
        analyzer.setOriginFile(originFile);
        analyzer.setComparisonFile(fileToCompare);
        analyzer.setComparisionStrategy(comparisionStrategy);
        return analyzer;
    }

    public void setOriginFile(File originFile) {
        validateIfFileExists(originFile);
        this.originFile = originFile;
    }

    public void setComparisonFile(File comparisonFile) {
        validateIfFileExists(comparisonFile);
        this.comparisonFile = comparisonFile;
    }

    public void setComparisionStrategy(ElementComparisonStrategy comparisionStrategy) {
        this.comparisionStrategy = comparisionStrategy;
    }

    private static void validateIfFileExists(File file) {
        if (!file.exists()) {
            var errorMessage = String.format("File [%s] does not exist.", file.getAbsolutePath());
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public Optional<MatchedElement> GetMatchedElement(String targetElementId) {
        Optional<MatchedElement> matchedElement = Optional.empty();
        var targetElement = getTargetElement(targetElementId);
        var comparisonDoc = getComparisonDoc();
        if(targetElement.isPresent() && comparisonDoc.isPresent()) {
            matchedElement = comparisionStrategy.GetMatchedElement(targetElement.get(), comparisonDoc.get());
        }
        return matchedElement;
    }

    private Optional<Element> getTargetElement(String targetElementId) {
        try {
            var doc = Jsoup.parse(
                    originFile,
                    StandardCharsets.UTF_8.name(),
                    originFile.getAbsolutePath());

            var element = doc.getElementById(targetElementId);
            if(null == element){
                LOGGER.info(String.format("Element with id [%s] not found", targetElementId));
            }
            return Optional.ofNullable(element);
        } catch (IOException e) {
            LOGGER.error("Error reading [{}] file", originFile.getAbsolutePath(), e);
            return Optional.empty();
        }
    }

    private Optional<Document> getComparisonDoc() {
        try {
            var doc = Jsoup.parse(
                    comparisonFile,
                    StandardCharsets.UTF_8.name(),
                    comparisonFile.getAbsolutePath());

            return Optional.ofNullable(doc);
        } catch (IOException e) {
            LOGGER.error("Error reading [{}] file", comparisonFile.getAbsolutePath(), e);
            return Optional.empty();
        }
    }
}
