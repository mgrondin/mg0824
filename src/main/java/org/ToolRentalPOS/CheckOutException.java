package org.ToolRentalPOS;

//I created this class so that CheckOut would have it's own exceptions.
public class CheckOutException extends Exception{

    public CheckOutException (){
        super();
    }

    public CheckOutException (Throwable cause){
        super(cause);
    }

    public CheckOutException(String message){
        super(message);
    }

    public CheckOutException (String message, Throwable cause){
        super(message,cause);
    }

}
