



%md We've already created a Parquet table containing popular first names by gender and year, for all years between 1880 and 2014. (This data comes from the United States Social Security Administration.) We can create a DataFrame from that data, by calling `sqlContext.read.parquet()`.

**NOTE**: That's the Spark 1.4 API. The API is slightly different in Spark 1.3.

// COMMAND ----------

// Spark 1.4 and 1.5
val df = sqlContext.read.parquet("part-r-00001.gz.parquet")

// Spark 1.3
//val df = sqlContext.parquetFile("dbfs:/mnt/training/ssn/names.parquet")

// COMMAND ----------

%md Let's cache it, to speed things up.

// COMMAND ----------

df.cache()


// COMMAND ----------

%md Let's take a quick look at the first 20 items of the data.

// COMMAND ----------

df.show()





// COMMAND ----------

%md Take a look at the data schema, as well. Note that, in this case, the schema was read from the columns (and types) in the Parquet table.

// COMMAND ----------

df.printSchema()

// COMMAND ----------

%md You can create a new DataFrame that looks at a subset of the columns in the first DataFrame.

// COMMAND ----------

val firstNameDF = df.select("firstName", "year")

// COMMAND ----------

%md Then, you can examine the values in the `nameDF` DataFrame, using an action like `show()` 

// COMMAND ----------

firstNameDF.show()

// COMMAND ----------

%md You can also count the number of items in the data set...

// COMMAND ----------

firstNameDF.count()

// COMMAND ----------

%md ...or determine how many distinct names there are.

// COMMAND ----------

firstNameDF.select("firstName").distinct.count()

// COMMAND ----------

%md Let's do something a little more complicated. Let's use the original data frame to find the five most popular names for girls born in 1980.

**Things to Notes**

1. Look closely, and you'll see a `desc` after the `orderBy()` call. `orderBy()` (which can also be invoked as `sort()`) sorts in ascending order. Adding the `desc` suffix causes the sort to be in _descending_ order.
2. The Scala DataFrames API's comparison operator is `===` (_triple_ equals), not the usual `==` (_double_ equals). If you get it wrong, you'll get a Scala compiler error.

// COMMAND ----------

(df.filter(df("year") === 1980).
           filter(df("gender") === "F").
           orderBy(df("total").desc, df("firstName")).
           select("firstName").
           limit(5)).show 

// COMMAND ----------

%md You can also use the `$` interpolator syntax to produce column references:

// COMMAND ----------

(df.filter($"year" === 1980).
           filter($"gender" === "F").
           orderBy($"total".desc, $"firstName").
           select("firstName").
           limit(5)).show 

// COMMAND ----------

%md Note that we can do the same thing using the lower-level RDD operations. However, use the DataFrame operations, when possible. In general, they're more convenient. More important, though, they allow Spark to build a query plan that can be optimized through [Catalyst](

// COMMAND ----------

%md ## Joins

// COMMAND ----------

%md Let's use two views of our data to answer this question: How popular were the top 10 female names of 1890 back in 1880?

Before we can do that, though, we need to define a utility function. The DataFrame SCala API doesn't support the SQL `LOWER` function. To ensure that our data matches up properly, it'd be nice to force the names to lower case before doing the match. Fortunately, it's easy to define our own `LOWER` function:

// COMMAND ----------

val lower = sqlContext.udf.register("lower", (s: String) => s.toLowerCase)

// COMMAND ----------

%md Okay, now we can go to work.

// COMMAND ----------

// Create a new DataFrame from the SSNA DataFrame, so that:
// - We have a lower case version of the name, for joining
// - We've weeded out the year
//
// NOTE: The aliases are necessary; otherwise, the query analyzer
// generates false equivalences between the columns.
val ssn1890 = df.filter($"year" === 1890).
                 select($"total".as("total1890"), 
                        $"gender".as("gender1890"), 
                        lower($"firstName").as("name1890"))

// Let's do the same for 1880.
val ssn1880 = df.filter($"year" === 1880).
                 select($"total".as("total1880"), 
                        $"gender".as("gender1880"), 
                        lower($"firstName").as("name1880"))

// Now, let's find out how popular the top 10 New York 1890 girls' names were in 1880.
val joined = ssn1890.join(ssn1880, ($"name1890" === $"name1880") && ($"gender1890" === $"gender1880")).
                     filter($"gender1890" === "F").
                     orderBy($"total1890".desc).
                     limit(10).
                     select($"name1890".as("name"), $"total1880", $"total1890")

joined.show


