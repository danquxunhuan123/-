package com.trs.waijiaobu.bean;

import java.util.List;

/**
 * creator：liufan
 * data: 2019/7/23
 */
public class Channel {

    private List<GdEntity> gd;
    private List<MoreEntity> more;

    public List<GdEntity> getGd() {
        return gd;
    }

    public void setGd(List<GdEntity> gd) {
        this.gd = gd;
    }

    public List<?> getMore() {
        return more;
    }

    public void setMore(List<MoreEntity> more) {
        this.more = more;
    }

    public class MoreEntity {
        /**
         * lmt : 2012-11-20 18:10:50
         * documents : http://www.fmprc.gov.cn/chn/gxh/tyb/mobile/xw_589601/dnzt_589607/wjbdjjyo_613148/documents.json
         * cname : 温家宝总理出席第九届亚欧首脑会议并对老挝进行正式访问
         * logo :
         * type : 1
         * cid : 175020
         */
        private String lmt;
        private String documents;
        private String cname;
        private String logo;
        private String type;
        private String cid;

        public void setLmt(String lmt) {
            this.lmt = lmt;
        }

        public void setDocuments(String documents) {
            this.documents = documents;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getLmt() {
            return lmt;
        }

        public String getDocuments() {
            return documents;
        }

        public String getCname() {
            return cname;
        }

        public String getLogo() {
            return logo;
        }

        public String getType() {
            return type;
        }

        public String getCid() {
            return cid;
        }
    }

    public static class GdEntity {
        /**
         * cid : 162592
         * cname : 重要新闻
         * type : 13
         * lmt : 2012-07-30 13:31:10
         * logo :
         * documents : http://www.fmprc.gov.cn/chn/gxh/tyb/mobile/xw_589601/zyxw_589603/documents.json
         */

        private String cid;
        private String cname;
        private String type;
        private String lmt;
        private String logo;
        private String desc;
        private String documents;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getLmt() {
            return lmt;
        }

        public void setLmt(String lmt) {
            this.lmt = lmt;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getDocuments() {
            return documents;
        }

        public void setDocuments(String documents) {
            this.documents = documents;
        }
    }
}
