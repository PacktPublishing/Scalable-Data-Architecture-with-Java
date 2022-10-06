# Scalable Data Architecture with Java

<a href="https://www.packtpub.com/product/scalable-data-architecture-with-java/9781801073080"><img src="https://static.packt-cdn.com/products/9781801073080/cover/smaller" alt="Scalable Data Architecture with Java" height="256px" align="right"></a>

This is the code repository for [Scalable Data Architecture with Java](https://www.packtpub.com/product/scalable-data-architecture-with-java/9781801073080), published by Packt.

**Build efficient enterprise-grade data architecting solutions using Java**

## What is this book about?
Java architectural patterns and tools help architects to build reliable, scalable, and secure data engineering solutions that collect, manipulate, and publish data.
This book will help you make the most of the architecting data solutions available with clear and actionable advice from an expert.

You’ll start with an overview of data architecture, exploring responsibilities of a Java data architect, and learning about various data formats, data storage, databases, and data application platforms as well as how to choose them. Next, you’ll understand how to architect a batch and real-time data processing pipeline. You’ll also get to grips with the various Java data processing patterns, before progressing to data security and governance. The later chapters will show you how to publish Data as a Service and how you can architect it. Finally, you’ll focus on how to evaluate and recommend an architecture by developing performance benchmarks, estimations, and various decision metrics.

By the end of this book, you’ll be able to successfully orchestrate data architecture solutions using Java and related technologies as well as to evaluate and present the most suitable solution to your clients.

This book covers the following exciting features: 
* Analyze and use the best data architecture patterns for problems
* Understand when and how to choose Java tools correctly for a data architecture
* Build batch and real-time data engineering solutions using Java
* Discover how to apply security and governance to a solution
* Measure performance, publish benchmarks, and optimize solutions
* Evaluate, choose, and present the best architectural alternatives
* Understand how to publish Data as a Service using GraphQL and a REST API	

If you feel this book is for you, get your [copy](https://www-amazon-in.translate.goog/-/hi/Sinchan-Banerjee-ebook/dp/B0B5LFLFDM?_x_tr_sl=hi&_x_tr_tl=en&_x_tr_hl=en&_x_tr_pto=sc) today!

<a href="https://www.packtpub.com/?utm_source=github&utm_medium=banner&utm_campaign=GitHubBanner"><img src="https://raw.githubusercontent.com/PacktPublishing/GitHub/master/GitHub.png" alt="https://www.packtpub.com/" border="5" /></a>

## Instructions and Navigations
All of the code is organized into folders.

The code will look like the following:
```
public interface Transformer<K, V, R> {
void init(ProcessorContext var1);
R transform(K var1, V var2);
void close();
}
```
Any command-line input or output is written as follows:
```
bin/connect-standalone.sh config/connect-standalone.properties connect-riskcalc-mongodb-sink.properties
```

**Following is what you need for this book:**
Data architects, aspiring data architects, Java developers and anyone who wants to develop or optimize scalable data architecture solutions using Java will find this book useful. A basic understanding of data architecture and Java programming is required to get the best from this book.	

With the following software and hardware list you can run all code files present in the book (Chapter 1-12).

### Software and Hardware List

| Chapter  | Software required                                                                    | OS required                        |
| -------- | -------------------------------------------------------------------------------------| -----------------------------------|
|  1-12		   |   		Java SDK 8 or 11					                                            			  | Windows, Mac OS, and Linux  |
|  1-12		   |   Apache Maven 3.6 or above					                                            			  | Windows, Mac OS, and Linux  |
|  1-12		   |   		IntelliJ IDEA Community Edition					                                            			  | Windows, Mac OS, and Linux  |
|  1-12		   |   		Apache Spark 3.0 or above				                                            			  |  Windows, Mac OS, and Linux |
|  1-12		   |   		AWS S3, Lambda, EMR, ECR, API Gateway					                                            			  | AWS CLoud|
|  1-12		   |   		Docker Desktop					                                            			  | Windows, Mac OS, and Linux  |
|  1-12		   |   	Minikube v1.23.2					                                            			  | Windows, Mac OS, and Linux  |
|  1-12		   |   	PostgreSQL 14.0					                                            			  | Windows, Mac OS, and Linux  |
|  1-12		   |   MongoDB Atlas					                                            			  | AWS CLoud|
|  1-12		   |   	Apache Kafka 2.8.2				                                            			  | Windows, Mac OS, and Linux  |
|  1-12		   |   	Apache NIFI 1.12.0					                                            			  | Windows, Mac OS, and Linux  |
|  1-12		   |   	DataHub				                                            			  | Docker/Kubernetes|
|  1-12		   |   Postman					                                            			  |  Windows, Mac OS, and Linux |
|  1-12		   |   	GraphQL Playground 1.8.10				                                            			  | Windows, Mac OS, and Linux  |
|  1-12		   |   	JMeter 5.5					                                            			  | Windows, Mac OS, and Linux  |

We also provide a PDF file that has color images of the screenshots/diagrams used in this book. [Click here to download it](https://packt.link/feLcH).


### Related products <Other books you may enjoy>
* Data Cleaning and Exploration with Machine Learning [[Packt]](https://www.packtpub.com/product/data-cleaning-and-exploration-with-machine-learning/9781803241678?_ga=2.220177373.554494994.1663753571-1347501151.1654864057) [[Amazon]](https://www.amazon.com/-/es/Michael-Walker/dp/1803241675/ref=sr_1_1?__mk_es_US=%C3%85M%C3%85%C5%BD%C3%95%C3%91&crid=22BJ26J6GKAH5&keywords=Data+Cleaning+and+Exploration+with+Machine+Learning&qid=1663850961&sprefix=%2Caps%2C1519&sr=8-1)

* Production-Ready Applied Deep Learning [[Packt]](https://www.packtpub.com/product/production-ready-applied-deep-learning/9781803243665?_ga=2.178153825.554494994.1663753571-1347501151.1654864057) [[Amazon]](https://www.amazon.com/-/es/Tomasz-Palczewski/dp/180324366X/ref=sr_1_1?__mk_es_US=%C3%85M%C3%85%C5%BD%C3%95%C3%91&crid=14OVKC9DYTF1H&keywords=Production-Ready+Applied+Deep+Learning&qid=1663856745&sprefix=data+cleaning+and+exploration+with+machine+learning%2Caps%2C997&sr=8-1)

## Get to Know the Author(s)
**Sinchan Banerjee** is a principal data architect at UST Inc. He works for their client Anthem to architect, build, and deliver scalable, robust data engineering solutions to solve their business problems. Prior to his journey with UST, he worked for various Fortune 500 organizations, such as Amex, Optum, Impetus, and HP, designing, architecting, and building robust data engineering solutions for very high volumes of data. He is the lead author of a patent on storage capacity forecasting and is the co-author of multiple international publications. He is also a certified AWS Professional and a certified Java programmer. He has also been a recipient of multiple awards and accolades for exceptional technical contribution, leadership, and innovation.
