package com.agileengine.smartxml;

import org.jsoup.nodes.Element;

import java.util.Collections;
import java.util.stream.Collectors;

public final class XPath
{
    public static String generate(Element element) {
        var elements = element.parents();
        elements.add(0, element);
        Collections.reverse(elements);
        var selector = elements.stream()
                .map(e -> {
                    var elementsByTagName = e.parent().children().stream().filter(c -> c.tagName().equals(e.tagName()));
                    var iterator = elementsByTagName.iterator();
                    int tagSiblingIndex = 1;
                    while (iterator.hasNext()) {
                        var el = iterator.next();
                        if (el.siblingIndex() == e.siblingIndex()) {
                            return String.format("%s[%d]", e.nodeName(), tagSiblingIndex);
                        }
                        tagSiblingIndex++;
                    }
                    return null;
                })
                .collect(Collectors.joining("/"));
        return "/" + selector;
    }
}
