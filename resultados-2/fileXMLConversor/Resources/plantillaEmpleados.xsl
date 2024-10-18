<?xml version="1.0" encoding='ISO-8859-1'?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
 <xsl:template match='/'>
   <html><xsl:apply-templates /></html>
 </xsl:template>
 <xsl:template match='Empleados'>
    <head><title>LISTADO DE EMPLEADOS</title></head>
    <body> 
    <h1>LISTADO DE EMPLEADOS</h1>
    <table border='1'>
    <tr><th>Identificador</th><th>Apellido</th><th>Departamento</th><th>Salario</th><th>Cargo</th></tr>
      <xsl:apply-templates select='Empleado' />
    </table>
    </body>
 </xsl:template>
 <xsl:template match='Empleado'>
   <tr><xsl:apply-templates /></tr>
 </xsl:template>
 <xsl:template match='Identificador|Apellido|Departamento|Salario|Cargo'>
   <td><xsl:apply-templates /></td>
 </xsl:template>
</xsl:stylesheet>