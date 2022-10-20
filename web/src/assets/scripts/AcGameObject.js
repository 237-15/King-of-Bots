const AC_GAME_OBJECT = [];  //存储所有的游戏对象

export class AcGameObject {
    constructor() {  //构造函数
        AC_GAME_OBJECT.push(this);  //将当前对象加到数组中去
        this.has_called_start = false;  //判断 start 函数是否执行
        this.timedelta = 0;  //当前时间
    }

    start() {  //只执行一次

    }

    update() {  //每一帧执行一次，除了第一帧以外

    }

    on_destroy() {  //删除之前执行

    }

    destroy() {
        this.on_destroy();  //先执行 on_destroy 函数

        for(let i in AC_GAME_OBJECT) {
            const obj = AC_GAME_OBJECT[i];
            if(obj === this) {
                AC_GAME_OBJECT.splice(i, 1);  //删除下标为 i 的函数，并且只删除一个，若不写 1 ，则删除下标位置往后的所有元素
            }
        }
    }
}

let last_timedelta;  //上一次的时间
const step = timedelta => {  //step是函数
    for(let obj of AC_GAME_OBJECT) {
        if (!obj.has_called_start) {
            obj.has_called_start = true;
            obj.start();
        } else {
            obj.timedelta = timedelta - last_timedelta;
            obj.update();
        }
    }
    last_timedelta = timedelta;
    requestAnimationFrame(step);  //递归
}

requestAnimationFrame(step);
