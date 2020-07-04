package requests;

import model.enums.ReservationStatus;
import spark.Request;

public class ReservationSearch {
	
	private String guest = null;
	private ReservationStatus status = null;
	
	public ReservationSearch(Request req) {
		this.guest = req.queryParams("guest");
		
		String sStatus = req.queryParams("status");
		if(sStatus != null && (sStatus.equals("Created") || sStatus.equals("Declined") || sStatus.equals("Canceled") || sStatus.equals("Accepted") || sStatus.equals("Completed")))
			this.status = ReservationStatus.valueOf(sStatus);
	}
	public String getGuest() {
		return guest;
	}
	public void setGuest(String guest) {
		this.guest = guest;
	}
	public ReservationStatus getStatus() {
		return status;
	}
	public void setStatus(ReservationStatus status) {
		this.status = status;
	}

}
