package com.agileengine.smartxml.comparison;

import com.agileengine.smartxml.MatchedElement;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Optional;

public interface ElementComparisonStrategy {

    Optional<MatchedElement> GetMatchedElement(Element targetElement, Document comparisonDoc);
}
