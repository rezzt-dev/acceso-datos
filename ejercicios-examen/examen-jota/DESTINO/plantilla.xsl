<?xml version="1.0" encoding='ISO-8859-1'?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
 <xsl:template match='/'>
   <html><xsl:apply-templates /></html>
 </xsl:template>
 <xsl:template match='Universidades'>
    <head><title>LISTADO DE UNIVERSIDADES</title></head>
    <body> 
    <h1>LISTA DE UNIVERSIDADES</h1>
    <table border='1'>
    <tr><th>ID</th><th>Ciudad</th><th>Carrera</th><th>Nota de corte</th></tr>
      <xsl:apply-templates select='Universidad' />
    </table>
    </body>
 </xsl:template>
 <xsl:template match='Universidad'>
   <tr><xsl:apply-templates /></tr>
 </xsl:template>
 <xsl:template match='Identificador|Ciudad|Carrera|Nota'>
   <td><xsl:apply-templates /></td>
 </xsl:template>
</xsl:stylesheet>
