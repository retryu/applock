package com.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.dao.interfaze.VerfyDao;
import com.dao.model.inter.Verfy;
import com.db.OrmDateBaseHelper;
import com.widget.LockPatternView;
import com.widget.LockPatternView.Cell;

public class LockPatternUtils {

	// private static final String TAG = "LockPatternUtils";
	private final static String KEY_LOCK_PWD = "lock_pwd";

	private static int MIN_LENGHT = 4;
	private static Context mContext;

	private static SharedPreferences preference;

	private static VerfyDao verfyDao;

	// private final ContentResolver mContentResolver;

	public LockPatternUtils(Context context) {
		mContext = context;
		preference = PreferenceManager.getDefaultSharedPreferences(mContext);
		// mContentResolver = context.getContentResolver();
	}

	/**
	 * Deserialize a pattern.
	 * 
	 * @param string
	 *            The pattern serialized with {@link #patternToString}
	 * @return The pattern.
	 */
	public static List<LockPatternView.Cell> stringToPattern(String string) {
		List<LockPatternView.Cell> result = new ArrayList<LockPatternView.Cell>();

		final byte[] bytes = string.getBytes();
		for (int i = 0; i < bytes.length; i++) {
			byte b = bytes[i];
			result.add(LockPatternView.Cell.of(b / 3, b % 3));
		}
		return result;
	}

	/**
	 * Serialize a pattern.
	 * 
	 * @param pattern
	 *            The pattern.
	 * @return The pattern in string form.
	 */
	public static String patternToString(List<LockPatternView.Cell> pattern) {
		if (pattern == null) {
			return "";
		}
		final int patternSize = pattern.size();

		byte[] res = new byte[patternSize];
		for (int i = 0; i < patternSize; i++) {
			LockPatternView.Cell cell = pattern.get(i);
			res[i] = (byte) (cell.getRow() * 3 + cell.getColumn());
		}
		return Arrays.toString(res);
	}

	public void saveLockPattern(List<LockPatternView.Cell> pattern) {
		// Editor editor = preference.edit();
		// String pass=patternToString(pattern);
		// editor.putString(KEY_LOCK_PWD, pass);
		// editor.commit();
		try {
			Verfy verfy = verfyDao.queryForId("1");
			verfy.setType("2");
			verfy.setPassword(patternToString(pattern));
			verfyDao.update(verfy);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getLockPaternString() {
		// return preference.getString(KEY_LOCK_PWD, "");
		String pass = null;
		try {
			if (verfyDao == null) {
				Log.e("debug", "verfy is  null");
				OrmDateBaseHelper ormDateBaseHelper = new OrmDateBaseHelper(
						mContext, "lock.db", null, 1);
				verfyDao = ormDateBaseHelper.getVerfyDao();
			}

			Verfy verfy = verfyDao.queryForId("1");
			pass = verfy.getPassword();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pass;
	}

	public int checkPattern(List<LockPatternView.Cell> pattern) {
		String stored = getLockPaternString();
		if (stored != null) {
			return stored.equals(patternToString(pattern)) ? 1 : 0;
		}
		return -1;
	}

	public void clearLock() {
		saveLockPattern(null);
	}

	public static VerfyDao getVerfyDao() {
		return verfyDao;
	}

	public static void setVerfyDao(VerfyDao verfyDao) {
		LockPatternUtils.verfyDao = verfyDao;
	}

	public boolean check(List<Cell> patterns) {
		if (patterns.size() < MIN_LENGHT) {
			return false;
		}
		return true;
	}

}
