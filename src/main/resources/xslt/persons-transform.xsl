<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
        version="1.0"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="xml" indent="yes"/>

    <xsl:template match="/persons">
        <people>
            <xsl:attribute name="count">
                <xsl:value-of select="count(person)"/>
            </xsl:attribute>

            <xsl:for-each select="person">
                <personSummary>
                    <personId>
                        <xsl:value-of select="id"/>
                    </personId>

                    <fullName>
                        <xsl:value-of select="concat(firstName, ' ', lastName)"/>
                    </fullName>

                    <contactEmail>
                        <xsl:value-of select="email"/>
                    </contactEmail>

                    <adult>
                        <xsl:choose>
                            <xsl:when test="number(age) &gt;= 18">true</xsl:when>
                            <xsl:otherwise>false</xsl:otherwise>
                        </xsl:choose>
                    </adult>
                </personSummary>
            </xsl:for-each>
        </people>
    </xsl:template>

</xsl:stylesheet>
