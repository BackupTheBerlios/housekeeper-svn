<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/TR/xhtml1/strict">
	
<xsl:output method="xml" encoding="iso-8859-1" />

<xsl:template match="usecases">
	<html>
		<head>
			<title>Usecases</title>
		</head>
		<body>
			<xsl:for-each select="usecase">
				<p>
					<xsl:apply-templates />
				</p>
			</xsl:for-each>
		</body>
	</html>
</xsl:template>

<xsl:template match="name">
	<h1>
		<xsl:number level="multiple"/> 
		<xsl:apply-templates />
	</h1>
</xsl:template>

<xsl:template match="description">
	<p>
		<xsl:number level="multiple"/>
		<xsl:apply-templates />
	</p>
</xsl:template>


</xsl:stylesheet>