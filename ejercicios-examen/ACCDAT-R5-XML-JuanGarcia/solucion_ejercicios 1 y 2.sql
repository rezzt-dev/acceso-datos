-----------------------------------------------------
--NOMBRE Y APELLIDOS: Juan Garcia Cazallas
--NOTA:
--OBSERVACIONES:
-----------------------------------------------------


-----------------------------------------------------
--EJERCICIO 1: 
--NOTA:
--OBSERVACIONES:
-----------------------------------------------------

a) /clientes/clien/nombre/text()
-----------------------------------------------------
b) /detallefacturas/factura[@numero="11"]/node()
-----------------------------------------------------
c) /detallefacturas/factura[codigo="FACT11"]/(codigo,referencia)
-----------------------------------------------------
d) /detallefacturas/string(sum(factura/producto/unidades))
-----------------------------------------------------
e) /clientes/clien[contains(tlf, "91")]
-----------------------------------------------------
f) /detallefacturas/string(round(avg(factura/producto/unidades)))
-----------------------------------------------------




-----------------------------------------------------
--EJERCICIO 2: 
--NOTA:
--OBSERVACIONES:
-----------------------------------------------------

a) update insert 
<producto descuento="0.13"><codigo>5</codigo><unidades>6</unidades></producto> 
into /detallefacturas/factura[codigo="FACT10"]
-----------------------------------------------------
b) update replace /detallefacturas/factura[codigo="FACT10"]/producto[codigo="2"]/unidades with <unidades>33</unidades>
-----------------------------------------------------
c) update value /detallefacturas/factura[codigo="FACT10"]/codigo with "FACTMODIF10"
-----------------------------------------------------
d) update delete /detallefacturas/factura[codigo="FACT10"]/producto[codigo="5"]
-----------------------------------------------------
e) update rename /detallefacturas/factura/referencia as "refer"



-----------------------------------------------------
--EJERCICIO 3:
--NOTA:
--OBSERVACIONES:
-----------------------------------------------------
private String eliminarNamespace (XQResultItem inputItem) {
  StringWriter writer = new StringWriter();

  try {
    Node node = (Node) inputItem.getNode();

    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
    transformer.transform(new DOMSource(node), new StreamResult(writer));
  } catch (Exception ex) {
    ex.printStackTrace();
  }

  return writer.toString();
}

public void MostrarClientes () {
  String inputConsulta = "/clientes/clien/(nombre,poblacion,tlf,direccion)";
    
  try {
    XQPreparedExpression xqConsulta = connection.prepareExpression(inputConsulta);
    XQResultSequence xqResultado = xqConsulta.executeQuery();
      
    XQResultItem resultItem;
    while (xqResultado.next()) {
      resultItem = (XQResultItem) xqResultado.getItem();

      if (resultItem.getItemType().getBaseType() == XQItemType.XQBASETYPE_STRING) {
        System.out.println(resultItem.getAtomicValue());
      } else {
        System.out.println(eliminarNamespace(resultItem));
      }
    }
  } catch (XQException ex) {
    ex.printStackTrace();
  }
}

-----------------------------------------------------
--EJERCICIO 4:
--NOTA:
--OBSERVACIONES:
-----------------------------------------------------
public void EliminarProductos () {
  String inputConsulta = "update delete /productos/product[@categoria='A' and number(@pvp)<200]";
    
  try {
    XQExpression xqConsulta = connection.createExpression();
      
    xqConsulta.executeCommand(inputConsulta);
    System.out.println("  - productos borrados correctamente.");
  } catch (XQException ex) {
    ex.printStackTrace();
  }
}
-----------------------------------------------------
