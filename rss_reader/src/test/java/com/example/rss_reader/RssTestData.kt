package com.example.rss_reader

object RssTestData {
    const val testRssInput = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><rss version=\"2.0\"\n" +
            "\txmlns:content=\"http://purl.org/rss/1.0/modules/content/\"\n" +
            "\txmlns:wfw=\"http://wellformedweb.org/CommentAPI/\"\n" +
            "\txmlns:dc=\"http://purl.org/dc/elements/1.1/\"\n" +
            "\txmlns:atom=\"http://www.w3.org/2005/Atom\"\n" +
            "\txmlns:sy=\"http://purl.org/rss/1.0/modules/syndication/\"\n" +
            "\txmlns:slash=\"http://purl.org/rss/1.0/modules/slash/\"\n" +
            "\t>\n" +
            "\n" +
            "<channel>\n" +
            "\t<title>TechCrunch</title>\n" +
            "\t<atom:link href=\"https://techcrunch.com/feed/\" rel=\"self\" type=\"application/rss+xml\" />\n" +
            "\t<link>https://techcrunch.com/</link>\n" +
            "\t<description>Startup and Technology News</description>\n" +
            "\t<lastBuildDate>Thu, 12 Oct 2023 08:11:11 +0000</lastBuildDate>\n" +
            "\t<language>en-US</language>\n" +
            "\t<sy:updatePeriod>\n" +
            "\thourly\t</sy:updatePeriod>\n" +
            "\t<sy:updateFrequency>\n" +
            "\t1\t</sy:updateFrequency>\n" +
            "\t<generator>https://wordpress.org/?v=6.3.1</generator>\n" +
            "\n" +
            "<image>\n" +
            "\t<url>https://techcrunch.com/wp-content/uploads/2015/02/cropped-cropped-favicon-gradient.png?w=32</url>\n" +
            "\t<title>TechCrunch</title>\n" +
            "\t<link>https://techcrunch.com/</link>\n" +
            "\t<width>32</width>\n" +
            "\t<height>32</height>\n" +
            "</image> \n" +
            "\t<item>\n" +
            "\t\t<title>&#8220;thousands&#8221; of pieces of content removed</title>\n" +
            "\t\t<link>https://this.is.link.com</link>\n" +
            "\t\t\n" +
            "\t\t<dc:creator><![CDATA[Ingrid Lunden]]></dc:creator>\n" +
            "\t\t<pubDate>Thu, 12 Oct 2023 08:11:11 +0000</pubDate>\n" +
            "\t\t\t\t<category><![CDATA[Government & Policy]]></category>\n" +
            "\t\t<category><![CDATA[Social]]></category>\n" +
            "\t\t<category><![CDATA[european commission]]></category>\n" +
            "\t\t<category><![CDATA[harmful content]]></category>\n" +
            "\t\t<category><![CDATA[thierry-breton]]></category>\n" +
            "\t\t<category><![CDATA[Twitter]]></category>\n" +
            "\t\t<category><![CDATA[X]]></category>\n" +
            "\t\t<guid isPermaLink=\"false\">https://techcrunch.com/?p=2613589</guid>\n" +
            "\n" +
            "\t\t\t\t\t<description><![CDATA[This is the description]]></description>\n" +
            "\t\t\n" +
            "\t\t\n" +
            "\t\t\n" +
            "\t\t\t</item>\n" +
            "\t\t</channel>\n" +
            "</rss>"

    const val invalidTestRssInput =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?><rss version=\"2.0\"\n" +
                "\t>\n" +
                "\n" +
                "<channel>\n" +
                "\t<title>TechCrunch<title>\n" +
                "</rss>"
}
