# SmartXmlAnalyzer
Console application that analyzes HTML and finds a specific element, even after changes, using a set of extracted attributes. 

## Environment requirements 

* JDK 11

## Execute app

1. Download the dist file `dist/smart-xml-analyzer.jar` you can find it [here](/dist/smart-xml-analyzer.jar).
2. From the the terminal execute 
`java -jar smart-xml-analyzer.jar <input_origin_file_path> <input_other_sample_file_path> <target_element_id>`. All the
parameters are required.

## Output

**sample-1-evil-gemini.html**
 
  ```
  XPath: /html[1]/body[1]/div[1]/div[1]/div[3]/div[1]/div[1]/div[2]/a[1]
  ```
 

**sample-2-container-and-clone.html**
 
  ```
 XPath: /html[1]/body[1]/div[1]/div[1]/div[3]/div[1]/div[1]/div[2]/div[1]/a[1]
  ```

**sample-3-the-escape.html**
 
  ```
XPath: /html[1]/body[1]/div[1]/div[1]/div[3]/div[1]/div[1]/div[2]/a[1]
  ```

**sample-4-the-mash.html**
 
  ```
XPath: /html[1]/body[1]/div[1]/div[1]/div[3]/div[1]/div[1]/div[3]/a[1]
  ```

