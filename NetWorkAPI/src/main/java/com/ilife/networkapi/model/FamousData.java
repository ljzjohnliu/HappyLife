package com.ilife.networkapi.model;

import java.util.List;

/**
 * {
 *     "code":200,
 *     "msg":"success",
 *     "newslist":[
 *         {
 *             "id":9583,
 *             "content":"仅是天才不能成为作家，因为书的背后极需要作家失格",
 *             "mrname":"爱献生"
 *         },
 *         {
 *             "id":9583,
 *             "content":"微贱往往是非野心的阶梯，凭借着它一步步爬上了高处；当他一旦登上了最高的一级之后，他便不再回顾那梯子；他的眼光彩夺目仰望云宵，瞧不起他从前所恃为凭借的低下的阶梯。",
 *             "mrname":"莎士比亚"
 *         },
 *         {
 *             "id":9583,
 *             "content":"以思想和力量来胜过别人的人，我并不称他们为英雄，只有以心灵使自己更伟大的人们，我才称为英雄。",
 *             "mrname":"罗曼·罗兰"
 *         }
 *     ]
 * }
 */
public class FamousData {

    private int code;
    private String msg;
    private List<FamousContentData> newslist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<FamousContentData> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<FamousContentData> newslist) {
        this.newslist = newslist;
    }

    public class FamousContentData{
        private int id;
        private String content;
        private String mrname;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getMrname() {
            return mrname;
        }

        public void setMrname(String mrname) {
            this.mrname = mrname;
        }


    }
}
