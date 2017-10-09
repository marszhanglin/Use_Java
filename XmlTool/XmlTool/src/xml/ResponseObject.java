package xml;

import java.util.List;

import com.google.gson.Gson;

public class ResponseObject {

	private ResponseState responseState;
	private pagingInfo pagingInfo;
	private List<item> data;

	public class ResponseState {
		private String description;
		private int code;
		private Object errorList;

	}

	public class pagingInfo {
		private String totalCount;
		private String pageSize;
		private String curPageNum;
		private String totalPage;
	}

	public class item {
		private String terminalSN;
		private String equipmentNum;
		private String applyModName;
		private String applyVersion;
		private String suffix;
		private String packages;
		private String fail;
		private String para;
		private String appUpdate;
		private String paraUpdate;

	}

	@Override
	public String toString() {

		return new Gson().toJson(this);
	}

}
