//This shit is not working, so don't even fucking try to execute it

//##############################
//Do it yourself Dependency Injection
//mögliche Klausuraufgabe

//This class is used to inject the correct Decoder into the AddressInspector class
public class Injector {
  public AddressInspector inject(){
    IGeoDecoder decoder;
    String decoderToUse = System.getProperty("decoderToUse");
    switch(decoderToUse){
      case "mapQuest": decoder = new MapQuestDecoder();
            break;
      case "tomtom": decoder = new TomTomDecoder();
            break;
      case "testdecoder": decoder = new TestDecoder();
            break;
    }
    return new AddressInspector(decoder);
  }
}





//Class where a Decoder is injected
public class AddressInspector {
public IGeoDecoder geoDecoder;

//Constructor Injection --> Ellies Favorite
public AddressInspector(IGeoDecoder decoder){
  this.geoDecoder = decoder;
}

//Setter Injection
//public void setGeoDecoder(IGeoDecoder decoder){
//  this.geoDecoder = decoder;
//}

public String validateUsingService(String address) {
boolean isValid = geoDecoder.validate(address);
String result = "Used " + geoDecoder.serviceName() + " to decode address: " +
"address is " + ((isValid) ? "valid" : "not valid");

return result;
 }
}





public Interface IGeoDecoder{
  boolean validate(String); //definded by AddressInspector
  String serviceName(); //defined by AddressInspector
}

public class MapQuestDecoder implements IGeoDecoder{
  @Override
  boolean validate(String address){
    //do stuff
    return true;
  }
  @Override
  String serviceName(){
    return getClass().getName();
  }
}

public class TomTomDecoder implements IGeoDecoder{
  @Override
  boolean validate(String address){
    //do stuff
    return true;
  }
  @Override
  String serviceName(){
    return getClass().getName();
  }
}

public class TestDecoder implements IGeoDecoder{
  @Override
  boolean validate(String address){
    //do stuff
    return true;
  }
  @Override
  String serviceName(){
    return getClass().getName();
  }
}

//##############################
//Dependency Injection in Jersey mit HK2
@Path("/address/vaidate")
class AddressInspector{
  public IGeoDecoder geoDecoder;

  @Inject
  public AddressInspector(IGeoDecoder decoder){
    this.geoDecoder = decoder;
  }

  @GET

}

public class DependencyBinder extends AbstractBinder{
  void configure(){
    bind(MapQuestDecoder).to(IDecoder.class);
  }
}

//No clue what dat shit is
MyApplication extends ResourceConfig{
  reg. DependencyBinder,...
}
