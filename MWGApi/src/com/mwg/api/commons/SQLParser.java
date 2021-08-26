package com.mwg.api.commons;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;

public class SQLParser {

	private String sql = "";

	private enum CheckPoint {
		CMT1, CMT2, FUNC, QUO1, QUO2
	};

	private static String delims = " \t\n\r\f;'`";

	private List<String> stmtList = new ArrayList<>();

	private Stack<CheckPoint> checkPointStack = new Stack<>();

	private static Set<String> _DDL_PREFIX_SET = new HashSet<String>();

	private static Set<String> _DML_PREFIX_SET = new HashSet<String>();

	private String invalidStmt = null;

	public String getInvalidStmt() {
		return invalidStmt;
	}

	static {
		_DDL_PREFIX_SET.add("SET");
		_DDL_PREFIX_SET.add("DROP");
		_DDL_PREFIX_SET.add("ALTER");
		_DDL_PREFIX_SET.add("CREATE");
		_DDL_PREFIX_SET.add("REPLACE");
		_DDL_PREFIX_SET.add("COMMENT");
		_DDL_PREFIX_SET.add("TRUNCATE");
		_DDL_PREFIX_SET.add("COMMIT");
		///////////////////////////////
		_DML_PREFIX_SET.add("INSERT");
		_DML_PREFIX_SET.add("UPDATE");
		_DML_PREFIX_SET.add("DELETE");
		_DML_PREFIX_SET.add("SELECT");
	}

	public SQLParser(String sql) {
		this.sql = sql;
		this.parse();
	}

	private void parse() {
		String stmt = "";
		StringTokenizer tokenizer = new StringTokenizer(sql, delims, true);
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			stmt = stmt + token;
			if (!checkPointStack.isEmpty()) {
				CheckPoint checkPoint = checkPointStack.peek();
				switch (checkPoint) {
				case CMT1:
					if (token.equals("\n") || token.equals("\r")) {
						checkPointStack.pop();
						stmt = "";
					}
					break;
				case CMT2:
					if (token.endsWith("*/")) {
						checkPointStack.pop();
						stmt = "";
					}
					break;
				case QUO1:
					if (token.equals("'")) {
						checkPointStack.pop();
					}
					break;
				case QUO2:
					if (token.equals("`")) {
						checkPointStack.pop();
					}
					break;
				case FUNC:
					if (isCompleteFunctionStmt(token, stmt)) {
						checkPointStack.pop();
						stmt = stmt.trim();
						if (isDDL(stmt) || isDML(stmt)) {
							stmtList.add(stmt);
						} else {
							invalidStmt = stmt;
							return;
						}
						stmt = "";
					}
					break;
				default:
					break;
				}
			} else {
				if (token.equals(";")) {
					stmt = stmt.trim();
					if (!stmt.isEmpty() && !stmt.equals(";")) {
						if (isDDL(stmt) || isDML(stmt)) {
							stmtList.add(stmt);
						} else {
							invalidStmt = stmt;
							return;
						}
					}
					stmt = "";
					continue;
				}
				if (token.startsWith("--") || token.startsWith("//")) {
					checkPointStack.push(CheckPoint.CMT1);
					continue;
				}
				if (token.startsWith("/*")) {
					checkPointStack.push(CheckPoint.CMT2);
					continue;
				}
				if (token.equals("'")) {
					checkPointStack.push(CheckPoint.QUO1);
					continue;
				}
				if (token.equals("`")) {
					checkPointStack.push(CheckPoint.QUO2);
					continue;
				}
				if (isCreateFunctionStmt(stmt)) {
					checkPointStack.push(CheckPoint.FUNC);
				}
			}
		}
		if (!checkPointStack.isEmpty()) {
			if (checkPointStack.pop().equals(CheckPoint.CMT1)) {
				stmt = "";
			}
		}
		if (!stmt.trim().isEmpty()) {
			this.invalidStmt = stmt;
		}
	}

	private boolean isCreateFunctionStmt(String sql) {
		String sqlFormat = sql.trim().replaceAll("\n|\r|\t|\f", " ").replaceAll(" +", " ").toUpperCase();
		return sqlFormat.startsWith("CREATE FUNCTION") || sqlFormat.startsWith("CREATE OR REPLACE FUNCTION");
	}

	private boolean isCompleteFunctionStmt(String token, String sql) {
		if (token.toLowerCase().endsWith("$function$")) {
			return StringUtils.countMatches(sql.toLowerCase(), "$function$") == 2;
		} else if (token.equals(";")) {
			return StringUtils.countMatches(sql, "$$") == 2 || StringUtils.countMatches(sql.toUpperCase(), "$BODY$") == 2;
		}
		return false;
	}

	public List<String> getStmtList() {
		return stmtList;
	}

	public Boolean hasError() {
		return invalidStmt != null;
	}

	public int countDDL() {
		return CollectionUtils.countMatches(stmtList, new Predicate() {

			@Override
			public boolean evaluate(Object stmt) {

				return isDDL(stmt.toString());
			}
		});
	}

	public int countDML() {
		return CollectionUtils.countMatches(stmtList, new Predicate() {

			@Override
			public boolean evaluate(Object stmt) {

				return isDML(stmt.toString());
			}
		});
	}

	public static Boolean isDDL(String sql) {
		String prefix = getFirstToken(sql);
		return prefix != null && _DDL_PREFIX_SET.contains(prefix.toUpperCase());
	}

	public static Boolean isDML(String sql) {
		String prefix = getFirstToken(sql);
		return prefix != null && _DML_PREFIX_SET.contains(prefix.toUpperCase());
	}

	public static String getFirstToken(String arg) {
		if (arg != null) {
			StringTokenizer token = new StringTokenizer(arg, delims, true);
			if (token.hasMoreTokens()) {
				return token.nextToken();
			}
		}
		return null;
	}
	
}
