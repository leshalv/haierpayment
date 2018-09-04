// 支持dev,run
var __env = 'dev';

var __appPath = location.pathname.replace(new RegExp('/', "gm"), '');
var __appCode = 'capitalmanage';
var __baseResourcePath = 'public';
var __Const = {
    init: {
        debug: false,
        appCode: false,
        login: false,
        show: {
            type: 'alert'
        },
        tabs: {
            menuRefresh: true
        },
        table: {
            columnNames: false
        }
    },
    url: {
        template: {
            todo: 'public/template/todo.html',
            confirm: 'public/template/modal/confirm.html',
            alert: 'public/template/modal/alert.html',
            exports: 'public/template/modal/export.html',
            imports: 'public/template/modal/import.html'
        },
        table: {
            page: '/common/table/page',
            select: '/common/table/select',
            deletes: '/common/table/deleteByIds'
        },
        form: {
            init: '/common/form/init',
            insert: '/common/form/insert',
            update: '/common/form/update',
            del: '/common/form/deleteById'
        },
        select: {
            query: '/common/select/query',
            init: '/common/select/init'
        },
        auto: {
            query: '/common/auto/query',
            init: '/common/auto/init'
        },
        authority: {
            info: '${root}/user',
            menus: '${portal}/mMenus/authMenus?appId=' + __appCode
        },
        excel: {
            upload: '/excel/upload',
            download: '/excel/download',
            template: '/excel/template'
        },
        cache: {
            entities: '/cache/entities',
            enums: '/cache/enums',
            dicts: '/cache/dicts',
            params: '/param/selectAllFromCache'
        },
        tree: {
            query: '/common/tree/query'
        },
        upload: {
            upload: '${root}/app/' + __appCode + '/file/uploadImg'
        },
        editor: {
            view: '${root}/app/' + __appCode + '/file/viewImg'
        }
    },
    rest: {
        head: true,
        baseUrl: window.location.protocol + "//" + window.location.host + '/'+ __appCode + '/api/message'
    },
    portal: {
        appId: 'portal',
        baseUrl: window.location.protocol + "//" + window.location.host + '/api'
    },
    menu: {
        parentId: 'parentMenu',
        url: 'menuUrl',
        buttonId: 'buttonId',
        buttonName: 'buttonName'
    },
    dicts: {
        //合作状态
        PL0201:{
            '00':'未启用',
            '10':'启用'
        },
        PL0402:{//合作项目模式
            '1':'联合放款模式'
        },
        PL0109:{//是-否
            '1':'是',
            '0':'否'
        },
        PL0104:{
            '1':'新增',
            '2':'修改',
            '3':'删除',
            '4':'其他'
        }
    }
};
