webpackJsonp([1],{"4/hK":function(t,e){},"7zck":function(t,e){},"Ijt+":function(t,e){},ImMs:function(t,e){},NHnr:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var i=n("7+uW"),a={data:function(){return{drawer:!1,items:[{action:"school",title:"Documentation",active:!0,items:[{title:"Javadoc"},{title:"Pydoc"}]}]}},methods:{navigateToHome:function(){this.$router.push("/")},goToDocumentation:function(t){var e=void 0;"Javadoc"===t?(e=window.open("https://ained.ttu.ee/javadoc/index.html","_blank"))&&e.focus():"Pydoc"===t&&(e=window.open("https://ained.ttu.ee/pydoc/index.html","_blank"))&&e.focus()}}},o={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",[n("v-navigation-drawer",{attrs:{fixed:"",app:"",temporary:""},model:{value:t.drawer,callback:function(e){t.drawer=e},expression:"drawer"}},[n("v-list",{attrs:{dense:""}},[n("v-list-tile",{on:{click:t.navigateToHome}},[n("v-list-tile-action",[n("v-icon",[t._v("home")])],1),t._v(" "),n("v-list-tile-content",[n("v-list-tile-title",[t._v("Home")])],1)],1),t._v(" "),t._l(t.items,function(e){return n("v-list-group",{key:e.title,attrs:{"prepend-icon":e.action,"no-action":""},model:{value:e.active,callback:function(n){t.$set(e,"active",n)},expression:"item.active"}},[n("v-list-tile",{attrs:{slot:"activator"},slot:"activator"},[n("v-list-tile-content",[n("v-list-tile-title",[t._v(t._s(e.title))])],1)],1),t._v(" "),t._l(e.items,function(e){return n("v-list-tile",{key:e.title,on:{click:function(n){t.goToDocumentation(e.title)}}},[n("v-list-tile-content",[n("v-list-tile-title",[t._v(t._s(e.title))])],1),t._v(" "),n("v-list-tile-action",[n("v-icon",[t._v(t._s(e.action))])],1)],1)})],2)})],2)],1),t._v(" "),n("v-toolbar",{attrs:{color:"cyan darken-3",dark:"",fixed:"",app:""}},[n("v-toolbar-side-icon",{on:{click:function(e){e.stopPropagation(),t.drawer=!t.drawer}}}),t._v(" "),n("v-toolbar-title",{style:{cursor:"pointer"},on:{click:t.navigateToHome}},[t._v("TTÜ Coding challenges")])],1)],1)},staticRenderFns:[]};var s={render:function(){var t=this.$createElement,e=this._self._c||t;return e("v-footer",{attrs:{color:"cyan darken-3",app:""}},[e("span",{staticClass:"white--text"},[this._v("© TTÜ 2018")])])},staticRenderFns:[]},l={components:{"app-nav-bar":n("VU/8")(a,o,!1,function(t){n("d0lg")},null,null).exports,"app-footer":n("VU/8")(null,s,!1,null,null,null).exports}},r={render:function(){var t=this.$createElement,e=this._self._c||t;return e("v-app",[e("app-nav-bar"),this._v(" "),e("v-content",[e("v-container",{attrs:{fluid:""}},[e("router-view")],1)],1),this._v(" "),e("app-footer")],1)},staticRenderFns:[]},c=n("VU/8")(l,r,!1,null,null,null).exports,u=n("/ocq"),h={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("v-container",{attrs:{"grid-list-md":"","text-xs-center":""}},[n("v-flex",{attrs:{xs12:""}},[n("v-alert",{attrs:{outline:"",color:"info",icon:"info",value:!0}},[t._v("\n      Choose challenge category you want to practice\n    ")])],1),t._v(" "),n("v-layout",{attrs:{row:"",wrap:""}},t._l(t.items,function(e,i){return n("v-flex",{key:i,attrs:{xs6:""}},[n("v-list",{attrs:{"two-line":""}},[n("v-list-tile",{on:{click:function(n){t.goToAccordingUri(e.title)}}},[n("v-list-tile-content",{on:{click:function(e){t.show=!t.show}}},[n("v-list-tile-title",{domProps:{innerHTML:t._s(e.title)}}),t._v(" "),n("v-list-tile-sub-title",{domProps:{innerHTML:t._s(e.subtitle)}})],1)],1)],1)],1)}))],1)},staticRenderFns:[]};var v=n("VU/8")({name:"LandingPage",data:function(){return{show:!0,items:[{title:"String",subtitle:"String manipulation with loops"},{title:"Array",subtitle:"Array manipulation with loops"},{title:"Algorithms",subtitle:"Usage of algorithms"},{title:"Functional",subtitle:"Lambda expressions"},{title:"Logic",subtitle:"Boolean logic puzzles"},{title:"Recursion",subtitle:"Problems with functions that call themselves"}]}},methods:{goToAccordingUri:function(t){var e="/"+t.toLowerCase();this.$router.push(e)}},created:function(){}},h,!1,function(t){n("Tm/R")},"data-v-1d888961",null).exports,d=n("mtWM"),m=n.n(d).a.create({baseURL:"http://localhost:8088",headers:{"Access-Control-Allow-Origin":"http://localhost:8080"}}),p=(n("t9xi"),n("Ijt+"),n("6S2o"),n("jQeI"),n("vq+x"),n("aX1R"),{name:"service",props:["items"],data:function(){return{challengeData:[],code:"",testerResult:"",errors:[],loader:null,loading:!1,cmOptions:{text:"hello",mode:"text/x-java",theme:"neat",lineNumbers:!0,line:!0,matchBrackets:!0,autoCloseBrackets:!0,lineWrapping:!0}}},methods:{sendCodeToTester:function(){var t=this;m.post("http://193.40.252.119:9090/test/sync",{name:"deniss",source:{files:[{path:this.challengeData.challengeName+"/Exam.java",content:this.code}]},project:{name:"SumString",course:"154858iapb_test"}}).then(function(e){t.testerResult=e.data.results[0]}).catch(function(t){console.log(t)})}},mounted:function(){this.challengeData=this.items,this.code=this.challengeData.challengeTemplate},computed:{codemirror:function(){return this.$refs.myCm.codemirror}},watch:{loader:function(){var t=this,e=this.loader;this[e]=!this[e],setTimeout(function(){return t[e]=!1},3e3),this.loader=null}}}),g={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("v-container",{attrs:{"grid-list-sm":""}},[n("v-layout",{attrs:{row:"",wrap:""}},[n("v-flex",{attrs:{xs7:""}},[n("v-jumbotron",{attrs:{height:"auto"}},[n("div",{staticClass:"title mb-3"},[t._v(t._s(t.challengeData.challengeName))]),t._v(" "),n("span",{staticClass:"subheading"},[t._v(" "+t._s(t.challengeData.challengeDescription))])]),t._v(" "),n("v-divider",{staticClass:"my-3"}),t._v(" "),n("codemirror",{staticClass:"CodeMirror",attrs:{options:t.cmOptions},model:{value:t.code,callback:function(e){t.code=e},expression:"code"}}),t._v(" "),n("v-btn",{staticClass:"white--text",attrs:{large:"",loading:t.loading,disabled:t.loading,color:"cyan darken-3"},on:{click:function(e){t.sendCodeToTester()}},nativeOn:{click:function(e){t.loader="loading"}}},[t._v("Submit")])],1),t._v(" "),n("v-flex",{attrs:{xs1:""}}),t._v(" "),n("v-flex",{attrs:{xs4:""}},[t._v("\n      "+t._s(t.testerResult)+"\n    ")])],1)],1)},staticRenderFns:[]};var f=n("VU/8")(p,g,!1,function(t){n("mkaV")},"data-v-3b76ec5c",null).exports,_={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("v-flex",{attrs:{xs3:""}},[n("v-select",{attrs:{items:t.states,"prepend-icon":"keyboard","hide-details":"",placeholder:"Java",overflow:""},model:{value:t.e1,callback:function(e){t.e1=e},expression:"e1"}})],1)},staticRenderFns:[]};var w={name:"StringChallenge",components:{challenge:f,"language-selector":n("VU/8")({data:function(){return{e1:"Java",states:["Java","Python"]}},watch:{e1:function(){this.$emit("langWasReset",this.e1)}}},_,!1,function(t){n("S5OY")},null,null).exports},data:function(){return{lang:"Java",showChallengeList:!0,showDetails:!1,items:[],itemDependingOnChallenge:[]}},methods:{chooseWhatToSendToPropAccordingToClickedName:function(t){for(var e=0;e<this.items.length;e++)this.items[e].challengeName===t&&(this.itemDependingOnChallenge=this.items[e])}},created:function(){var t=this;m.get("/challenges"+this.$route.path+"/"+this.lang.toLowerCase()).then(function(e){t.items=e.data}).catch(function(e){t.errors.push(e)}),this.$emit("clicked",this.response)},watch:{lang:function(){var t=this;m.get("/challenges/"+this.$route.path+"/"+this.lang.toLowerCase()).then(function(e){t.items=e.data}).catch(function(e){t.errors.push(e)}),this.$emit("clicked",this.response)}}},x={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",[n("v-container",{attrs:{"grid-list-md":"","text-xs-center":""}},[t.showChallengeList?n("v-flex",{attrs:{xs12:""}},[n("v-alert",{attrs:{outline:"",color:"info",icon:"info",value:!0}},[t._v("\n          Choose programming language and challenge\n        ")]),t._v(" "),n("language-selector",{on:{langWasReset:function(e){t.lang=e}}})],1):t._e(),t._v(" "),n("v-layout",{attrs:{row:"",wrap:""}},t._l(t.items,function(e,i){return t.showChallengeList?n("v-flex",{key:i,attrs:{xs4:"",md:""}},[n("v-list",{attrs:{"two-line":""}},[n("v-list-tile",{on:{click:function(n){t.showDetails=!t.showDetails,t.chooseWhatToSendToPropAccordingToClickedName(e.challengeName)}}},[n("v-list-tile-avatar",[n("v-icon",[t._v("code")])],1),t._v(" "),n("v-list-tile-content",{on:{click:function(e){t.showChallengeList=!t.showChallengeList}}},[n("v-list-tile-title",{domProps:{innerHTML:t._s(e.challengeName)}}),t._v(" "),n("v-list-tile-sub-title",{domProps:{innerHTML:t._s(e.challengeDifficulty)}})],1)],1)],1)],1):t._e()}))],1),t._v(" "),t.showDetails?n("challenge",{attrs:{items:t.itemDependingOnChallenge}}):t._e()],1)},staticRenderFns:[]};var k=n("VU/8")(w,x,!1,function(t){n("ImMs")},"data-v-3239ee96",null).exports;i.a.use(u.a);var b=new u.a({routes:[{path:"/",name:"LandingPage",component:v},{path:"/challenge",name:"Challenge",component:f},{path:"/string",name:"String",component:k},{path:"/array",name:"Array",component:k},{path:"/recursion",name:"Recursion",component:k},{path:"/logic",name:"Logic",component:k},{path:"/functional",name:"Functional",component:k},{path:"/algorithms",name:"Algorithms",component:k}]}),C=n("3EgV"),T=n.n(C),y=(n("7zck"),n("E5Az")),D=n.n(y);n("4/hK");i.a.use(T.a),i.a.use(D.a),i.a.config.productionTip=!1,new i.a({el:"#app",router:b,components:{App:c},template:"<App/>"})},S5OY:function(t,e){},"Tm/R":function(t,e){},d0lg:function(t,e){},mkaV:function(t,e){},t9xi:function(t,e){}},["NHnr"]);
//# sourceMappingURL=app.440f2ba945877e787c33.js.map