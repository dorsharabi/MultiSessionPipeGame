package Server;

public class RunTest {
	
    public static void main(String[] args) {
    	System.out.println("**** Server Side ****");

//        Server s = new MyServer(Integer.parseInt(args[0]));//Take the port from the args
       
        try {
            Server s = new MultiMyServer(6400,5);//Take the port from the args

        	s.start(new MyCHandler(null));
        	System.in.read();
            s.stop();
            System.out.println("Closed server");
        }catch(Exception e) {e.printStackTrace();}
    }
}
