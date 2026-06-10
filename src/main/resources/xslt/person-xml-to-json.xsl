<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
        version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="text" encoding="UTF-8"/>

    <xsl:template match="/">
        <xsl:text>{</xsl:text>

        <xsl:text>"name":"</xsl:text>
        <xsl:value-of select="normalize-space(//*[local-name()='name'])"/>
        <xsl:text>",</xsl:text>

        <xsl:text>"surname":"</xsl:text>
        <xsl:value-of select="normalize-space(//*[local-name()='surname'])"/>
        <xsl:text>",</xsl:text>

        <xsl:text>"age":</xsl:text>
        <xsl:value-of select="normalize-space(//*[local-name()='age'])"/>
        <xsl:text>,</xsl:text>

        <xsl:text>"address":"</xsl:text>
        <xsl:value-of select="normalize-space(//*[local-name()='address'])"/>
        <xsl:text>"</xsl:text>

        <xsl:text>}</xsl:text>
    </xsl:template>

</xsl:stylesheet>
