## Aspect Based Sentiment Analysis ##

This project describes aspect based sentiment analysis for review sentiment for an organization in Glassdoor.
The source file is extracted from Glassdoor reviews and can be found at reviews.ods

The source file goes under the following changes - 

1. ODS to text conversion - This step all the reviews are converted to flat texts in a text file.
2. Entity Set generation - The text file is then cleaned and goes under PoS tagging. The tagged text are then extracted using rules and   written to a csv file.
3. Word Cloud Generation - The CSV file is read by a RR Script (RServe-REngine) script to generate word cloud.
4. Classification Dictionary - Using the entity set we create a classification dictionary manually as which entity belongs to which class is a manual process.
5. Sentiment Analysis - We generate entity sentiment relationship from the review text using Stanford NLP library. The entity sentiment is then classified to different classes.
6. Sentiment Chart - Using R Script (RServe-REngine) a bar chart of entity sentiment is drawn.

# How to Run the Application

1. Install R & RServe: install.packages("Rserve")
2. In R console execute : library(Rserve)
3. In R console execute: Rserve(args="--no-save") this will start RServe on default port 6311

We can now integrate R with Java
4. Download tanford-corenlp-caseless-2015-04-20-models.jar and add to classpath
5. Make sure all the path (input/output directory, R script path, csv file path, ods file path and text file path are correct)
6. Execute Main.Java

This will generate the following output

1. Word Cloud

![wordcloud](https://cloud.githubusercontent.com/assets/2116198/22873855/943dfade-f1e8-11e6-80cf-9474fa889a47.png)


2. Sentiment Analysis

![review-sentiment](https://cloud.githubusercontent.com/assets/2116198/22873883/c4d8b922-f1e8-11e6-813b-8b2bcad91ff2.png)
