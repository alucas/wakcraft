package heero.wakcraft.havenbag;

import java.util.HashMap;
import java.util.Map;

public class HavenBagProperties {
	public static final String ACL_KEY_ALL = "@all";
	public static final String ACL_KEY_GUILD = "@guild";

	/** Used to allow visitor to enter in your haven bag */
	public boolean locked;
	/** Used to keep the acces permition of players */
	public Map<String, Integer> acl;

	public HavenBagProperties() {
		super();

		acl = new HashMap<String, Integer>();
		acl.put(ACL_KEY_ALL, 0);
		acl.put(ACL_KEY_GUILD, 0);
	}
}
