package heero.mc.mod.wakcraft.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HavenBagProperties {
	public static final String ACL_KEY_ALL = "@all";
	public static final String ACL_KEY_GUILD = "@guild";

	/** Used to allow visitor to enter in your haven bag */
	private boolean locked;
	/** Used to keep the acces permition of players */
	private Map<String, Integer> acl;

	public HavenBagProperties() {
		super();

		acl = new HashMap<String, Integer>();
		acl.put(ACL_KEY_ALL, 0);
		acl.put(ACL_KEY_GUILD, 0);
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLock(boolean locked) {
		this.locked = locked;
	}

	public Set<String> getRightKeys() {
		return acl.keySet();
	}

	public Integer getRight(String key) {
		return acl.get(key);
	}

	public void setRight(String key, Integer right) {
		acl.put(key, right);
	}
}
