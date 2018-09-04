define([
    'vue',
    'jquery',
    'util',
    'Const'
], function (vue, $, util, Const) {
    vue.component('bv-tree', {
        props: {
            entity: {
                default: function () {
                    return {};
                }
            },
            name: {
                default: ''
            },

            id: {
                default: ''
            },
            clazz: {
                default: ''
            },
            // 属性定义
            attr: {
                default: function () {
                    return {};
                }
            },
            // 类型，支持：menu-菜单
            type: '',
            // 初始化数据url
            url: {
                default: Const.url.tree.query
            },
            data: {
                default: function () {
                    return {};
                }
            },
            // 初始化method
            /*method: {
                default: 'post'
            },*/
            entityName: {
                default: ''
            },
            initParamList: {
                default: ''
            },
            orders: {
                default: ''
            },
            orderList: {
                default: ''
            },
            initEntity: {
                default: ''
            },
            // 是否显示图标
            icon: {
                default: true
            },
            // 是否显示连接线
            line: {
                default: true
            },
            // 是否允许选择
            check: {
                default: false
            },
            // 默认为空，可设置为s-表示父节点影响子节点，但子节点不影响父节点
            checkType: {
                default: ''
            },
            // 根节点
            rootNode: {
                default: ''
            },

            // 函数，初始化完成调用
            /// onInit: '',
            // 函数，组装数据
            pack: {
                default: ''
            }
            // 函数，展开前
            /// beforeExpand: '',
            // 展开时
            /// onExpand: '',
            // 函数，点击触发
            /// onClick: ''
        },
        beforeCreated: function () {
            this.nodes = [];
            // 树的jquery对象
            this.$tree = '';
            this.config = '';
        },
        created: function() {
            if (this.id) {
                this.attr.id = this.id;
            } else {
                this.attr.id = util.guid();
            }
            this.orderList = util.transOrder(this.orderList, this.orders);
        },
        mounted: function() {
            this.init();
        },
        methods: {
            init: function() {
                var callback = {};
                /*if (util.type(this.beforeExpand) === 'function') {
                    callback.beforeExpand = this.beforeExpand;
                }
                if (util.type(this.onExpand) === 'function') {
                    callback.onExpand = this.onExpand;
                }
                if (util.type(this.onClick) === 'function') {
                    callback.onClick = this.onClick;
                }*/
                var vm = this;
                callback.beforeExpand = function (treeId, treeNode) {
                    /*var menuTree = $.fn.zTree.getZTreeObj(treeId);
                    // 展开的所有节点，这是从父节点开始查找（也可以全文查找）
                    var expandedNodes = menuTree.getNodesByParam('open', true, treeNode.getParentNode());

                    for (var i = expandedNodes.length - 1; i >= 0; i--) {
                        var node = expandedNodes[i];
                        if (treeNode.id != node.id && node.level == treeNode.level && node.level !== 0) {
                            menuTree.expandNode(node, false);
                        }
                    }*/
                    vm.$emit('before-expand', treeId, treeNode);
                };
                callback.onExpand = function () {
                    vm.$emit('on-expand');
                };
                callback.onClick = function (e, treeId, treeNode) {
                    if (vm.type === 'menu' && treeNode.isParent) {
                        util.expand(treeId, treeNode);
                    }
                    vm.$emit('on-click', e, treeId, treeNode);
                };
                if (this.check) {
                    var vm = this;
                    callback.onCheck = function(event, treeId, treeNode) {
                        var checked = [];
                        var checkedNodes = vm.$tree.getCheckedNodes();
                        if (checkedNodes && checkedNodes.length > 0) {
                            for (var i=0; i<checkedNodes.length; i++) {
                                if (checkedNodes[i].entity) {
                                    checked = checked.concat(checkedNodes[i].entity);
                                }
                            }
                        }
                        if (vm.name) {
                            vm.entity[vm.name] = checked;
                        }
                    };
                }
                this.config = {
                    view: {
                        showIcon: this.icon,
                        showLine: this.line,
                        showTitle: false,
                        selectedMulti: !this.check,
                        dblClickExpand: false
                    },
                    check: {
                        enable: this.check,
                        chkboxType: this.checkType ? {Y: this.checkType, N: this.checkType} : {Y: 'ps', N: 'ps'}
                    },
                    data: {
                        simpleData: {
                            enable: true
                        }
                    },
                    callback: callback
                };
                var data = vm.data;
                /*if (vm.appCode) {
                    data.appCode = vm.appCode;
                }*/
                if (vm.entityName) {
                    data.entityName = vm.entityName;
                }
                if (!util.isEmpty(vm.initParamList)) {
                    data.initParamList = vm.initParamList;
                }
                if (!util.isEmpty(vm.initEntity)) {
                    data = util.mix(data, vm.initEntity);
                }
                if (vm.orderList && vm.orderList.length > 0) {
                    data.orderList = vm.orderList;
                }
                if (util.type(vm.url) === 'string') {
                    if (util.endsWith(vm.url, ".json")) {
                        $.getJSON(vm.url, function(res) {
                            vm.dataInit(res);
                        });
                    } else {
                        util.request({
                            type: 'post',
                            url: vm.url,
                            //appCode: vm.appCode,
                            data: data,
                            success: function(res) {
                                if (util.data(res)) {
                                    vm.dataInit(util.data(res));
                                }
                            }
                        });
                    }
                } else if (util.type(vm.url) === 'array') {
                    vm.dataInit(vm.url);
                }
            },
            dataInit: function(data) {
                if (data) {
                    this.nodes = [];
                    if (this.rootNode) {
                        this.nodes.push(this.rootNode);
                    }
                    for (var i=0; i<data.length; i++) {
                        this.nodes.push(this.pack.call(null, data[i]));
                    }
                    this.$tree = $.fn.zTree.init($(this.$el), this.config, this.nodes);

                    this.$emit('on-init', this);
                    /*if (util.type(this.onInit) === 'function') {
                        this.onInit.call(null, this);
                    }*/
                }
            }
        },
        /****** 模板定义 ******/
        template: util.template('bv-tree')
    });
});