package com.mwg.api.commons;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

import com.mwg.api.utils.IOUtils;
import com.stevesoft.pat.Regex;

public class TxtTemplatePat {

	private String Error = "";
	private String Template = "";
	private final String VarOpen = "\\{\\{";
	private final String VarClose = "\\}\\}";
	private final String BlockStart = "<!--BEGIN ";
	private final String BlockEnd = "<!--END ";
	private final String BlockClose = "-->";
	private final Map<String, String> BlockValue = new HashMap<>();

	public TxtTemplatePat(InputStream stream, String charset) throws Exception {
		this.Template = IOUtils.toString(stream, charset);
	}

	public TxtTemplatePat(String template) throws Exception {
		this.Template = template;
	}

	// //////////////////////////////////////////////////////////////////////
	public String GetError() {
		return this.Error;
	}

	// ////////////////////////////////////////////////////////
	public void SetVariable(final String varname, String value) {
		final String pattern = this.VarOpen + varname + this.VarClose;
		final Regex reg = new Regex(pattern, value != null ? Matcher.quoteReplacement(value) : "");
		Template = reg.replaceAll(Template);
	}

	// ///////////////////////////////////////////////////////
	public void RemoveVar() {
		final String pattern = this.VarOpen + "[a-zA-Z0-9_]*" + this.VarClose;
		final Regex reg = new Regex(pattern, "");
		Template = reg.replaceAll(Template);
	}

	// ///////////////////////////////////////////////////////
	public void RemoveBlock(final String blockname) {
		final String pattern1 = this.BlockStart + blockname + this.BlockClose;
		final String pattern2 = this.BlockEnd + blockname + this.BlockClose;
		final String pattern = pattern1 + "((?s).*)" + pattern2;
		final Regex reg = new Regex(pattern, "");
		Template = reg.replaceAll(Template);
	}

	// ///////////////////////////////////////////////////////
	public void RemoveBlock() {
		final Set<String> keys = this.BlockValue.keySet();
		for (String key : keys) {
			this.RemoveBlock(key);
		}
	}

	// ///////////////////////////////////////////////////////////
	public boolean UpdateBlock(final String blockname) {
		final String pattern1 = this.BlockStart + blockname + this.BlockClose;
		final String pattern2 = this.BlockEnd + blockname + this.BlockClose;
		final String pattern = pattern1 + "((?s).*)" + pattern2;
		final Regex reg = new Regex(pattern, Matcher.quoteReplacement(pattern1 + pattern2));
		reg.search(this.Template.toString());
		if (reg.didMatch()) {
			this.BlockValue.put(blockname, reg.stringMatched(1));
			Template = reg.replaceAll(Template);
			return true;
		}
		return false;
	}

	// ///////////////////////////////////////////////////////////
	public void ParseBlock(final String blockname) {
		final String pattern1 = this.BlockStart + blockname + this.BlockClose;
		final String pattern2 = this.BlockEnd + blockname + this.BlockClose;
		final String pattern = pattern1 + "(?s).*" + pattern2;
		String rep = "";
		if (this.BlockValue.get(blockname) == null) {
			rep = "";
		} else {
			rep = this.BlockValue.get(blockname);
		}
		rep = rep + pattern1 + pattern2;
		final Regex reg = new Regex(pattern, Matcher.quoteReplacement(rep));
		Template = reg.replaceAll(Template);
	}

	// ///////////////////////////////////////////////////////////
	public String Parse() {
		this.RemoveVar();
		this.RemoveBlock();
		return Template;
	}

}
