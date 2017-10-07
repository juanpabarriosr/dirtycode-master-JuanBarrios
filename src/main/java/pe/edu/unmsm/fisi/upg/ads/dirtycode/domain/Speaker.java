package pe.edu.unmsm.fisi.upg.ads.dirtycode.domain;

import java.util.Arrays;
import java.util.List;

import pe.edu.unmsm.fisi.upg.ads.dirtycode.exceptions.NoSessionsApprovedException;
import pe.edu.unmsm.fisi.upg.ads.dirtycode.exceptions.SpeakerDoesntMeetRequirementsException;

public class Speaker {
	private String firstName;
	private String lastName;
	private String email;
	private int experience;
	private boolean hasBlog;
	private String blogURL;
	private WebBrowser browser;
	private List<String> certifications;
	private String employer;
	private int registrationFee;
	private List<Session> sessions;
	
	public Integer register(IRepository repository) throws Exception {
		Integer speakerId = null;
		boolean isGood = false;
		boolean isApproved = false;
		Integer numeroCertificaciones = 3;
		String[] oldTechnology = new String[] { "Cobol", "Punch Cards", "Commodore", "VBScript" };
		List<String> domains = Arrays.asList("aol.com", "hotmail.com", "prodigy.com", "compuserve.com");
		int experienceTop = 10;
		
		if (!this.firstName.isEmpty()) {
			if (!this.lastName.isEmpty()) {
				if (!this.email.isEmpty()) {
					
					List<String> employers = Arrays.asList("Pluralsight", "Microsoft", "Google", "Fog Creek Software", "37Signals", "Telerik");
					
					isGood = ((this.experience > experienceTop || this.hasBlog || this.certifications.size() > numeroCertificaciones || employers.contains(this.employer)));
					
					if (!isGood) {
						String[] emailSplitted = this.email.split("@");
						String emailDomain = emailSplitted[emailSplitted.length - 1];

						if (!domains.contains(emailDomain) && (!(browser.getName() == WebBrowser.BrowserName.InternetExplorer && browser.getMajorVersion() < 9)))
						{
							isGood = true;
						}
					}
					
					if (isGood) {
						
						if (this.sessions.size() == 0) throw new IllegalArgumentException("Can't register speaker with no sessions to present.");
						
						else{
							for (Session session : sessions) {
								
								for (String Technology : oldTechnology) {
									if (session.getTitle().contains(Technology) || session.getDescription().contains(Technology)) {
										session.setApproved(false);
										break;
									} else {
										session.setApproved(true);
										isApproved = true;
									}
								}
								
							}
						}  
						
						if (isApproved) {
							registrationFee();
							try {
								speakerId = repository.saveSpeaker(this);
							} catch (Exception e) {}
						} else {
							throw new NoSessionsApprovedException("No sessions approved.");
						}
					} else {
						throw new SpeakerDoesntMeetRequirementsException("Speaker doesn't meet our abitrary and capricious standards.");
					}
				} else {
					throw new IllegalArgumentException("Email is required.");
				}				
			} else {
				throw new IllegalArgumentException("Last name is required.");
			}			
		} else {
			throw new IllegalArgumentException("First Name is required");
		}
			
		//if we got this far, the speaker is registered.
		return speakerId;
	}

	private Integer registrationFee() {
			
		if (this.experience <= 1) {return 500;}
		else if (experience >= 2 && experience <= 3) {return 250;}
		else if (experience >= 4 && experience <= 5) {return 100;}
		else if (experience >= 6 && experience <= 9) {return 50;}
		else {return 0;}		
	}
	
	
	
// _________________________________________________________________________________________________	
	
	public List<Session> getSessions() {
		return sessions;
	}

	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getExp() {
		return experience;
	}

	public void setExp(int experience) {
		this.experience = experience;
	}

	public boolean isHasBlog() {
		return hasBlog;
	}

	public void setHasBlog(boolean hasBlog) {
		this.hasBlog = hasBlog;
	}

	public String getBlogURL() {
		return blogURL;
	}

	public void setBlogURL(String blogURL) {
		this.blogURL = blogURL;
	}

	public WebBrowser getBrowser() {
		return browser;
	}

	public void setBrowser(WebBrowser browser) {
		this.browser = browser;
	}

	public List<String> getCertifications() {
		return certifications;
	}

	public void setCertifications(List<String> certifications) {
		this.certifications = certifications;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public int getRegistrationFee() {
		return registrationFee;
	}

	public void setRegistrationFee(int registrationFee) {
		this.registrationFee = registrationFee;
	}
}