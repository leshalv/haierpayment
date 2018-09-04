define([
    'vue',
    'jquery',
    'util',
    'Const'
], function (vue, $, util, Const) {
    vue.component('bv-static', {
        props: {
            entity: {
                default: function () {
                    return {};
                }
            },

            id: {
                default: ''
            },
            name: {
                default: ''
            },
            // class
            clazz: {
                default: ''
            },
            // 属性定义
            attr: {
                default: function () {
                    return {};
                }
            },
            // 默认值
            defaultValue: {
                default: ''
            },
            // 初始值
            value: {
                default: ''
            },
            // 设置值
            text: {
                default: ''
            },
            from: {
                default: ''
            },
            // 格式化
            format: {
                default: ''
            },
            // 自定义显示
            show: {
                default: ''
            },
            // dicts来源于table
            dicts: {
                default: ''
            },
            preset: {
                default: ''
            },
            code: {
                default: ''
            },
            desc: {
                default: ''
            },
            // 字典项翻译
            choose: {
                default: ''
            }
        },
        data: function () {
            return {
                // 静态文本不需要多余的属性配置
                staticAttr: {},
                showText: ''
            }
        },
        created: function() {
            util.initDefault(this);
            this.showText = this.value;

            if (this.attr.id) {
                this.staticAttr.id = this.attr.id;
            } else if (this.id) {
                this.staticAttr.id = this.id;
            }
            if (this.attr.name) {
                this.staticAttr.name = this.attr.name;
            } else if (this.name) {
                this.staticAttr.name = this.name;
            }

            this.initAttr();
        },
        mounted: function () {
            if (this.from === 'table') {
                this.showText = this.text;
                if (this.format === 'dict') {
                    this.$watch('dicts.' + this.name + '.options', function (value, oldValue) {
                        this.initDictVal();
                    });
                }
            } else if (this.from === 'form') {
                if (this.format === 'dict') {
                    util.initSelectData(this, 'dict');
                    this.initVal(this.showText);
                }
                this.$watch('entity.' + this.name, function(value, oldValue) {
                    this.initAttr();
                    this.initVal(value);
                });
            } else if (this.from === 'operate') {
                this.showText = this.text;
            }
            if (this.format === 'dict') {
                this.initDictVal();
            } else {
                this.initVal(this.showText);
            }
            /*if (this.format === 'dict') {
                this.initVal(this.value);
            }*/
        },
        /*mounted: function () {
            if (this.from === 'table') {
                this.value = this.text;
                this.$watch('text', function(val, oldVal) {
                    this.initAttr();
                    this.value = val;
                });
            } else {
                this.initVal(this.value);
                this.$watch('entity.' + this.name, function(val, oldVal) {
                    this.initAttr();
                    this.initVal(val);
                });
            }
        },*/
        methods: {
            initAttr: function () {
                // this.innerClass = this.clazz || '';
                /// this.attr = {};//this.attr || {};
                if (this.attr.title) {
                    this.staticAttr.title = this.attr.title;
                    this.staticAttr['data-title'] = this.attr.title;
                    this.staticAttr['data-original-title'] = this.attr.title;
                    this.clazz += 'abbr';
                    /*if (!this.href) {
                        this.clazz += 'abbr';
                    }*/
                }
                if (this.from) {
                    this.clazz += ' from-' + this.from;
                }
            },
            initVal: function (val) {
                if (this.show && util.type(this.show) === 'function') {
                    this.showText = util.format(this.show.call(null, val, this.name), this.format);
                } else {
                    this.showText = util.format(util.trans(val, {
                        // preset: this.preset,
                        code: this.code,
                        desc: this.desc,
                        options: this.options
                    }), this.format);
                }
            },
            initDictVal: function () {
                if (this.dicts && this.dicts[this.name] && this.dicts[this.name].options && this.dicts[this.name].options.length > 0) {
                    for (var i=0; i<this.dicts[this.name].options.length; i++) {
                        if (this.entity[this.name] === this.dicts[this.name].options[i][this.code]) {
                            this.showText = this.dicts[this.name].options[i][this.desc] || this.dicts[this.name].options[i][this.code];
                        }
                    }
                }
            }
        },
        /****** 模板定义 ******/
        template: util.template('bv-static')
    });

    vue.component('bv-textfield', {
        props: {
            entity: {
                default: function () {
                    return {};
                }
            },

            id: {
                default: ''
            },
            name: {
                default: ''
            },
            // class
            clazz: {
                default: ''
            },
            // 属性定义
            attr: {
                default: function () {
                    return {};
                }
            },
            // 默认值
            defaultValue: {
                default: ''
            },
            // 初始值
            value: {
                default: ''
            },

            // 数据校验
            validate: {
                default: ''
            },
            // 输入限制
            fix: {
                default: ''
            },

            type: {
                default: 'text'
            },
            chooseUrl: {
                default: ''
            },
            onChoosePreset: {
                default: ''
            }
        },
        created: function() {
            util.initDefault(this);
            util.initId(this);
            if (!this.attr.type) {
                this.attr.type = this.type;
            }
        },
        methods: {
            checkInput: function(event) {
                if (this.fix) {
                    this.entity[this.name] = util.fix(event, this.fix);
                }
            },
            openModal: function () {
                if (util.type(this.onChoosePreset) === 'function') {
                    this.onChoosePreset.call(null);
                }
                util.modal({
                    url: this.chooseUrl,
                    vm: this
                    /*callback: function () {
                        console.log('xxxxxx');
                    }*/
                });
            }
        },
        /****** 模板定义 ******/
        template: util.template('bv-textfield')
    });

    vue.component('bv-hidden', {
        props: {
            entity: {
                default: function () {
                    return {};
                }
            },

            id: {
                default: ''
            },
            name: {
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
            defaultValue: {
                default: ''
            },
            value: {
                default: ''
            }
        },
        watch: {
            value: function (val, oldVal) {
                this.$emit('on-change', this.name, val, oldVal);
            }
        },
        created: function() {
            util.initDefault(this);
        },
        /****** 模板定义 ******/
        template: util.template('bv-hidden')
    });

    vue.component('bv-textarea', {
        props: {
            entity: {
                default: function () {
                    return {};
                }
            },

            id: {
                default: ''
            },
            name: {
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
            defaultValue: {
                default: ''
            },
            value: {
                default: ''
            },
            // 数据校验
            validate: {
                default: ''
            }
        },
        created: function() {
            util.initDefault(this);
            util.initId(this);
        },
        /****** 模板定义 ******/
        template: util.template('bv-textarea')
    });

    vue.component('bv-select', {
        props: {
            entity: {
                default: function () {
                    return {};
                }
            },

            id: {
                default: ''
            },
            name: {
                default: ''
            },
            // class
            clazz: {
                default: ''
            },
            // 属性定义
            attr: {
                default: function () {
                    return {};
                }
            },
            // 默认值
            defaultValue: {
                default: ''
            },
            // 初始值
            value: {
                default: ''
            },
            // 数据校验
            validate: {
                default: ''
            },
            load: {
                default: ''
            },

            // 字典类型，支持enums,dicts
            preset: {
                default: 'default'
            },
            code: {
                default: ''
            },
            desc: {
                default: ''
            },
            // 数据
            choose: {
                default: ''
            },
            // 数据接口
            url: {
                default: Const.url.select.query
            },
            method: {
                default: 'post'
            },
            entityName: {
                default: ''
            },
            initParam: {
                default: ''
            },
            initParamList: {
                default: function () {
                    return [];
                }
            },
            orders: {
                default: ''
            },
            // 翻译code用的字典
            trans: {
                default: ''
            },
            // 额外显示
            extras: {
                default: ''
            },
            // 不予显示的code值
            excludes: {
                default: ''
            },
            show: {
                default: ''
            },
            onChange: {
                default: ''
            },

            initOption: {
                default: '请选择'
            },
            multiple: {
                default: false
            },
            // select专用
            label: {
                default: ''
            }
        },
        data: function () {
            return {
                /// entity: this.entity,
                groups: [],
                options: []
            };
        },
        beforeCreate: function () {
            this.orderList = [];
            this.customLoad = false;
        },
        mounted: function () {
            if (this.url !== Const.url.select.query) {
                this.customLoad = true;
            }
            util.initDefault(this);
            util.initId(this);
            util.initSelectParam(this);

            var vm = this;
            if (this.load) {
                vm.$watch('entity.' + vm.load, function(val, oldVal) {
                    if (!util.isEmpty(val)) {
                        util.initSelectData(vm, 'select', 'load');
                    } else {
                        vm.entity[vm.name] = null;
                        vm.options = [];
                    }
                });
            }
            util.initSelectData(this, 'select');

            if (util.type(this.onChange) === 'function') {
                vm.$watch('entity.' + vm.name, function (val, oldVal) {
                    this.onChange.call(null, this.entity, this.entity[this.name], util.index(this.options, this.entity[this.name], this.code, true));
                })
            }
        },
        /****** 模板定义 ******/
        template: util.template('bv-select')
    });

    vue.component('bv-radio', {
        props: {
            entity: {
                default: function () {
                    return {};
                }
            },

            id: {
                default: ''
            },
            name: {
                default: ''
            },
            // class
            clazz: {
                default: ''
            },
            // 属性定义
            attr: {
                default: function () {
                    return {};
                }
            },
            // 默认值
            defaultValue: {
                default: ''
            },
            // 初始值
            value: {
                default: ''
            },
            // 数据校验
            validate: {
                default: ''
            },
            load: {
                default: ''
            },

            // 字典类型，支持enums,dicts
            preset: {
                default: 'default'
            },
            code: {
                default: ''
            },
            desc: {
                default: ''
            },
            // 数据
            choose: {
                default: ''
            },
            // 数据接口
            url: {
                default: Const.url.select.query
            },
            method: {
                default: 'post'
            },
            entityName: {
                default: ''
            },
            initParam: {
                default: ''
            },
            initParamList: {
                default: function () {
                    return [];
                }
            },
            orders: {
                default: ''
            },
            // 翻译code用的字典
            trans: {
                default: ''
            },
            // 额外显示
            extras: {
                default: ''
            },
            // 不予显示的code值
            excludes: {
                default: ''
            },
            show: {
                default: ''
            },
            onChange: {
                default: ''
            },

            // radio、checkbox专用
            labelClass: {
                default: ''
            },
            labelStyle: {
                default: ''
            },
            extraColumns: {
                default: ''
            },
            // 每行显示几列选项
            cols: {
                default: ''
            }
        },
        data: function () {
            return {
                /// entity: this.entity,
                options: []
            };
        },
        beforeCreate: function () {
            this.orderList = [];
            this.customLoad = false;
        },
        mounted: function () {
            if (this.url !== Const.url.select.query) {
                this.customLoad = true;
            }
            util.initDefault(this);
            util.initSelectParam(this);

            if (this.cols && this.cols > 0 && this.cols <= 12) {
                this.labelClass = 'col-custom col-md-' + (12 / this.cols);
            }

            var vm = this;
            if (this.load) {
                vm.$watch('entity.' + vm.load, function(val, oldVal) {
                    if (!util.isEmpty(val)) {
                        util.initSelectData(vm, 'radio', 'load');
                    } else {
                        vm.entity[vm.name] = null;
                        vm.options = [];
                    }
                });
            }
            util.initSelectData(this, 'radio');

            if (util.type(this.onChange) === 'function') {
                vm.$watch('entity.' + vm.name, function (val, oldVal) {
                    this.onChange.call(null, this.entity, this.entity[this.name], util.index(this.options, this.entity[this.name], this.code, true));
                })
            }
        },
        /****** 模板定义 ******/
        template: util.template('bv-radio')
    });

    vue.component('bv-checkbox', {
        props: {
            entity: {
                default: function () {
                    return {};
                }
            },

            id: {
                default: ''
            },
            name: {
                default: ''
            },
            // class
            clazz: {
                default: ''
            },
            // 属性定义
            attr: {
                default: function () {
                    return {};
                }
            },
            // 默认值
            defaultValue: {
                default: ''
            },
            // 初始值
            value: {
                default: ''
            },
            // 数据校验
            validate: {
                default: ''
            },
            load: {
                default: ''
            },

            // 字典类型，支持enums,dicts
            preset: {
                default: 'default'
            },
            code: {
                default: ''
            },
            desc: {
                default: ''
            },
            // 数据
            choose: {
                default: ''
            },
            // 数据接口
            url: {
                default: Const.url.select.query
            },
            method: {
                default: 'post'
            },
            entityName: {
                default: ''
            },
            initParam: {
                default: ''
            },
            initParamList: {
                default: function () {
                    return [];
                }
            },
            orders: {
                default: ''
            },
            // 翻译code用的字典
            trans: {
                default: ''
            },
            // 额外显示
            extras: {
                default: ''
            },
            // 不予显示的code值
            excludes: {
                default: ''
            },
            show: {
                default: ''
            },
            onChange: {
                default: ''
            },

            // radio、checkbox专用
            labelClass: {
                default: ''
            },
            extraColumns: {
                default: ''
            },
            // 每行显示几列选项
            cols: {
                default: ''
            },
            sticky: {
                default: ''
            }
        },
        data: function () {
            return {
                /// entity: this.entity,
                options: [],
                values: [],
                stickValue: this.sticky
            };
        },
        watch: {
            values: function (val, oldVal) {
                this.entity[this.name] = util.arrayToString(val, this.seprator);
            }
        },
        beforeCreate: function () {
            this.orderList = [];
            this.customLoad = false;
            this.seprator = ',';
        },
        mounted: function () {
            if (this.url !== Const.url.select.query) {
                this.customLoad = true;
            }
            if (this.sticky && util.startsWith(this.sticky, '#')) {
                this.stickValue = this.entity[this.sticky.substring(1)];
            }
            util.initDefault(this);
            util.initSelectParam(this);

            if (this.cols && this.cols > 0 && this.cols <= 12) {
                this.labelClass = 'col-custom col-md-' + (12 / this.cols);
            }

            if (util.type(this.value) !== 'array' ) {
                if (this.value) {
                    if (util.type(this.value) === 'string' && this.value.indexOf(this.seprator) !== -1) {
                        this.values = util.stringToArray(this.value, this.seprator);
                    } else{
                        this.values.push(this.value);
                    }
                }
            } else {
                this.values = this.value;
            }
            if (this.stickValue && !util.contains(this.values, this.stickValue)) {
                this.values.push(this.stickValue);
                var value = this.value;
                if (util.type(value) !== 'array' ) {
                    if (!value) {
                        value = this.stickValue;
                    } else {
                        value += this.seprator + this.stickValue;
                    }
                    this.entity[this.name] = value;
                }
            }

            var vm = this;
            if (this.load) {
                vm.$watch('entity.' + vm.load, function(val, oldVal) {
                    if (!util.isEmpty(val)) {
                        vm.entity[vm.name] = null;
                        vm.values = [];
                        util.initSelectData(vm, 'checkbox', 'load');
                    } else {
                        vm.entity[vm.name] = null;
                        vm.options = [];
                    }
                });
            }
            util.initSelectData(this, 'checkbox');

            if (util.type(this.onChange) === 'function') {
                vm.$watch('entity.' + vm.name, function (val, oldVal) {
                    this.onChange.call(null, this.entity, this.entity[this.name], util.index(this.options, this.entity[this.name], this.code, true));
                })
            }
        },
        /****** 模板定义 ******/
        template: util.template('bv-checkbox')
    });

    vue.component('bv-button', {
        props: {
            entity: {
                default: function () {
                    return {};
                }
            },

            id: {
                default: ''
            },
            name: {
                default: ''
            },
            // class
            clazz: {
                default: ''
            },
            // 属性定义
            attr: {
                default: function () {
                    return {};
                }
            },
            // 默认值
            defaultValue: {
                default: ''
            },
            // 初始值
            value: {
                default: ''
            },

            // 支持button，close
            type: {
                default: 'button'
            },

            loading: {
                default: ''
            },
            text: {
                default: ''
            },
            icon: {
                default: ''
            },
            click: {
                default: ''
            }
        },
        created: function() {
            util.initDefault(this);
            if (this.text) {
                this.value = this.text;
            }
            if (!this.clazz) {
                this.clazz = 'btn-default';
            }
            if (this.type === 'close') {
                this.icon = 'icon-cancel';
                this.attr['data-dismiss'] = 'modal';
            }
            if (this.loading) {
                this.attr['data-loading-text'] = this.loading;
            }
        },
        methods: {
            doClick: function (event) {
                if (util.type(this.click) === 'function') {
                    this.click.call(null, event, this.entity);
                }
            }
        },
        /****** 模板定义 ******/
        template: util.template('bv-button')
    });

    vue.component('bv-href', {
        props: {
            entity: {
                default: function () {
                    return {};
                }
            },

            id: {
                default: ''
            },
            name: {
                default: ''
            },
            // class
            clazz: {
                default: ''
            },
            // 属性定义
            attr: {
                default: function () {
                    return {};
                }
            },
            // 默认值
            defaultValue: {
                default: ''
            },
            // 初始值
            value: {
                default: ''
            },
            text: {
                default: ''
            },
            from: {
                default: ''
            },
            format: {
                default: ''
            },
            show: {
                default: ''
            },

            icon: {
                default: ''
            },
            href: {
                default: ''
            },
            click: {
                default: ''
            }
        },
        created: function() {
            util.initDefault(this);
            /*if (this.text) {
                this.value = this.text;
            }*/
            if (this.attr.title) {
                this.attr['data-title'] = this.attr.title;
                this.attr['data-original-title'] = this.attr.title;
            }
            if (this.href && this.href.type === 'sub') {
                this.icon = 'icon-more';
            }
        },
        mounted: function () {
            if (this.from === 'table') {
                this.value = this.text;
                this.$watch('text', function(val, oldVal) {
                    this.value = val;
                });
            } else {
                if (this.show && util.type(this.show) === 'function') {
                    this.value = util.format(this.show.call(null, this.value), this.format);
                } else {
                    this.value = util.format(this.value, this.format);
                }

                this.$watch('entity.' + this.name, function(val, oldVal) {
                    if (this.show && util.type(this.show) === 'function') {
                        this.value = util.format(this.show.call(null, val), this.format);
                    } else {
                        this.value = util.format(val, this.format);
                    }
                });
            }
        },
        methods: {
            // TODO: 暂时只支持table的column调用
            doClick: function (event) {
                if (util.type(this.click) === 'function') {
                    this.click.call(this.$parent, event);
                }
            }
        },
        /****** 模板定义 ******/
        template: util.template('bv-href')
    });
});