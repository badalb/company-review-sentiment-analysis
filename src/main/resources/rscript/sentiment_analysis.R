df1 <- read.csv('/Users/badalb/TravisCI/company-review-sentiment-analysis/src/main/resources/output/sentiment-output.csv', header=TRUE, sep=",")
barplot(`colnames<-`(t(df1[-1]), df1[,1]), beside=TRUE, 
        legend.text = TRUE, col = c("red", "green","blue"), 
        cex.axis=0.6, cex.names=0.6,
        args.legend = list(x = "topleft", bty = "n", inset=c(0.5, 0),cex=0.8, pt.cex = 1 ))
dev.copy(png,'/Users/badalb/TravisCI/company-review-sentiment-analysis/src/main/resources/output/myplot.png')
dev.off()