package com.agileengine.smartxml.comparison;

import com.agileengine.smartxml.MatchedElement;
import com.agileengine.smartxml.XPath;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Arrays;
import java.util.Optional;

public class DefaultComparisionStrategy implements ElementComparisonStrategy {
    private static final int ATTRIBUTE_VALUE_WORDS_MATCH_PERCENTAGE = 50;
    private static final int ATTRIBUTES_MATCH_PERCENTAGE = 65;
    private static final String ANY_WHITESPACE_REGEX = "\\s";

    @Override
    public Optional<MatchedElement> GetMatchedElement(Element targetElement, Document comparisonDoc) {
        Optional<MatchedElement> matchedElement = Optional.empty();
        var comparisonElements = comparisonDoc.getElementsByTag(targetElement.tagName());
        if (comparisonElements.isEmpty()) {
            return matchedElement;
        }
        for (var comparisonElement: comparisonElements) {
            var matchingPercentage = GetMatchingPercentage(targetElement, comparisonElement);
            if (ATTRIBUTES_MATCH_PERCENTAGE <= matchingPercentage) {
                var elementPath = XPath.generate(comparisonElement);
                matchedElement = Optional.of(new MatchedElement(elementPath));
                return matchedElement;
            }
        }
        return matchedElement;
    }

    private static double GetMatchingPercentage(Element targetElement, Element comparisonElement) {
        var matchedAttributesWithTarget = 0d;

        var comparisonAttributes = comparisonElement.attributes();
        var targetAttributes = targetElement.attributes();
        for (var targetAttr : targetAttributes) {
            var comparisonAttrValue = comparisonAttributes.get(targetAttr.getKey());
            if (comparisonAttrValue.isEmpty()) {
                continue;
            }
            var targetAttrValue = targetAttr.getValue();
            var targetAttrWords = targetAttrValue.split(ANY_WHITESPACE_REGEX);
            var containedWordsInAttr = CountContainedWordsInAttr(comparisonAttrValue, targetAttrWords);
            var wordsMatchPercentage = ((double) containedWordsInAttr/ targetAttrWords.length)* 100;

            if (ATTRIBUTE_VALUE_WORDS_MATCH_PERCENTAGE <= wordsMatchPercentage) {
                matchedAttributesWithTarget++;
            }
        }
        var matchingPercentage = (matchedAttributesWithTarget / targetAttributes.size()) * 100;
        return (int) matchingPercentage;
    }

    private static long CountContainedWordsInAttr(String comparisonAttributeValue,
                                                  String[] targetAttributeWords) {
        return Arrays.stream(targetAttributeWords)
                .filter(a -> comparisonAttributeValue.toLowerCase().contains(a.toLowerCase()))
                .count();
    }
}
