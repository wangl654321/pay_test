<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>菜单导航栏</title>
    <style>
        .db {
            background-color: #2c5fa0;
            font-size: larger;
            color: #ffffff;
            width: 166.5px;
            height: 40px;
            line-height: 40px;
            cursor: pointer;
        }

        .menu ul {
            list-style: none; /* 去掉ul前面的符号 */
            margin: 0px; /* 与外界元素的距离为0 */
            padding: 0px; /* 与内部元素的距离为0 */
            width: auto; /* 宽度根据元素内容调整 */
        }

        .menu ul li {
            float: left;
            position: relative;
            width: auto;
        }

        .menu ul li:hover .db {
            background-color: #0528a0;
        }

        .menu ul li ul {
            visibility: hidden;
            position: absolute;
            z-index: 9999; /* 拥有更高堆叠顺序的元素总是会处于堆叠顺序较低的元素的前面 */
            width: auto;
        }

        .menu ul li ul li {
            font-size: large;
            text-align: center;
            vertical-align: middle;
            background: #0528a0;
            width: auto;
            min-width: 166.5px;
            border-bottom: 0px solid white;
            white-space: nowrap;
            height: 40px;
            line-height: 40px;
        }

        .menu ul li ul li:hover {
            background-color: #2c5fa0;
        }

        .menu ul li ul li a {
            display: block;
            color: #ffffff;
            text-decoration: none;
            font-weight: 500;
            width: auto;
        }

        .menu ul li:hover ul {
            visibility: visible;
            display: block;
        }

        .menu ul li:hover ul li a:hover {
            font-size: 20px;
        }
    </style>
</head>
<body>
<div class="menu" style="margin-left: auto; margin-right: auto; text-align: center;">
    <ul>
        <li><div class="db">结算通</div>
            <ul>
                <li><a href='/xiaopu/topage.do'>小浦支付</a></li>
                <li><a href="/quickpay/newWap.do">网银支付2.0</a></li>
            </ul>
        </li>
    </ul>
</div>
</body>
</html>