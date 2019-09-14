Feature: Systembolaget projekt

Scenario Outline: Search for products på systemboget
Given Initialize the browser and navigate to Systembolaget home page
And Click on the adults button
And Check home page title and URL
When I search for <product>
Then It should be displayed at least one product
Then The products description contains <description>
Then The browser closed

Examples:
|product			|description														|
|anchor steam		|Maltig, fruktig smak med inslag av torkade aprikoser				|
|pappe				|Smakrikt, kryddigt vin med											|

