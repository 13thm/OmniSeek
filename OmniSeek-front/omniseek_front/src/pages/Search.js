import React, { useState, useEffect } from 'react';
import { searchAll } from '@/utils/api';
import './Search.css';


const Search = () => {
    // 状态管理
    const [keyword, setKeyword] = useState('');
    const [activeTab, setActiveTab] = useState('all'); // all/user/code/photo
    const [searchResults, setSearchResults] = useState({
        userList: [],
        codeList: [],
        pictureList: [],
        novelList: [],
    });
    const [loading, setLoading] = useState(false);

    // 初始加载：关键字为空，获取所有内容
    useEffect(() => {
        fetchSearchResults('');
    }, []);

    // 搜索请求逻辑
    const fetchSearchResults = (searchKeyword) => {
        setLoading(true);
        searchAll({
            keyword: searchKeyword,
            types: ['user', 'code', 'photo','novel'], // 始终搜索所有类型，前端控制展示
        })
            .then((res) => {
                const { data } = res;
                // 匹配后端真实返回结构：code=0 表示成功，data里是userList/codeList/pictureList
                if (data.code === 0 && data.message === 'ok') {
                    setSearchResults({
                        userList: data.data.userList || [],
                        codeList: data.data.codeList || [],
                        pictureList: data.data.pictureList || [],
                        novelList: data.data.novelList || [],
                    });
                } else {
                    alert(data.message || '搜索失败');
                }
            })
            .catch((err) => {
                console.error('搜索接口异常：', err);
                alert('网络异常，请重试');
            })
            .finally(() => {
                setLoading(false);
            });
    };

    // 搜索按钮点击事件
    const handleSearch = () => {
        fetchSearchResults(keyword);
    };

    // 输入框回车事件
    const handleKeyPress = (e) => {
        if (e.key === 'Enter') {
            handleSearch();
        }
    };

    // 渲染不同类型的结果（严格匹配后端返回字段，仅展示图片链接）
    const renderResultContent = () => {
        let renderData = [];
        // 根据选中的标签筛选对应数据
        switch (activeTab) {
            case 'user':
                renderData = searchResults.userList;
                break;
            case 'code':
                renderData = searchResults.codeList;
                break;
            case 'photo':
                renderData = searchResults.pictureList;
                break;
            case 'novel':
                renderData = searchResults.novelList;
                break;
            case 'all':
                // 合并所有类型结果并标记类型，方便渲染区分
                renderData = [
                    ...searchResults.userList.map((item) => ({ ...item, type: 'user' })),
                    ...searchResults.codeList.map((item) => ({ ...item, type: 'code' })),
                    ...searchResults.novelList.map((item) => ({ ...item, type: 'novel' })),
                    ...searchResults.pictureList.map((item) => ({ ...item, type: 'photo' })),
                ];
                break;
            default:
                renderData = [];
        }

        // 加载状态
        if (loading) {
            return <div className="empty-result">加载中...</div>;
        }

        // 空结果状态
        if (renderData.length === 0) {
            return <div className="empty-result">暂无相关结果</div>;
        }

        // 渲染具体结果
        return (
            <div className="search-result">
                {renderData.map((item, index) => {
                    // 渲染用户信息
                    if (item.type === 'user' || activeTab === 'user') {
                        return (
                            <div key={`user-${item.userId || index}`} className="result-item">
                                <h3>用户：{item.userName}</h3>
                                <p>用户ID：{item.userId}</p>
                                <p>手机号：{item.userPhone || '未填写'}</p>
                                <p>创建时间：{formatTime(item.createTime)}</p>
                                <p>更新时间：{formatTime(item.updateTime)}</p>
                                <p>删除状态：{item.isDelete === 0 ? '未删除' : '已删除'}</p>
                            </div>
                        );
                    }

                    // 渲染代码信息
                    if (item.type === 'code' || activeTab === 'code') {
                        return (
                            <div key={`code-${item.codeId || index}`} className="result-item">
                                <h3>代码 [ {item.codeLang} ]</h3>
                                <p>代码ID：{item.codeId}</p>
                                <p>所属用户ID：{item.userId}</p>
                                <p>代码内容：</p>
                                <pre className="code-pre">{item.codeContent}</pre>
                                <p>创建时间：{formatTime(item.createTime)}</p>
                                <p>更新时间：{formatTime(item.updateTime)}</p>
                                <p>删除状态：{item.isDelete === 0 ? '未删除' : '已删除'}</p>
                            </div>
                        );
                    }

                    // 渲染图片信息【仅展示链接，移除所有图片预览相关代码】
                    if (item.type === 'photo' || activeTab === 'photo') {
                        return (
                            <div key={`photo-${item.photoId || index}`} className="result-item">
                                <h3>图片：{item.photoName}</h3>
                                <p>图片ID：{item.photoId}</p>
                                <p>所属用户ID：{item.userId}</p>
                                <p>
                                    图片链接：
                                    <a
                                        href={item.photoUrl}
                                        target="_blank"
                                        rel="noopener noreferrer"
                                        className="photo-link"
                                    >
                                        {item.photoUrl}
                                    </a>
                                </p>
                                <p>创建时间：{formatTime(item.createTime)}</p>
                                <p>更新时间：{formatTime(item.updateTime)}</p>
                                <p>删除状态：{item.isDelete === 0 ? '未删除' : '已删除'}</p>
                            </div>
                        );
                    }
                    if (item.type === 'novel' || activeTab === 'novel') {
                        return (
                            <div key={`novel-${item.id || index}`} className="result-item">
                                <h3>名字：{item.novelName}</h3>
                                <p>ID：{item.id}</p>
                                <p>描述：{item.description}</p>
                            </div>
                        );
                    }
                    return null;
                })}
            </div>
        );
    };

    // 时间格式化工具函数（处理后端返回的ISO时间）
    const formatTime = (timeStr) => {
        if (!timeStr) return '无';
        const date = new Date(timeStr);
        return date.toLocaleString('zh-CN', {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit',
            second: '2-digit',
        });
    };

    return (
        <div className="search-container">
            {/* 搜索框区域 */}
            <div className="search-header">
                <input
                    type="text"
                    className="search-input"
                    placeholder="请输入搜索关键字..."
                    value={keyword}
                    onChange={(e) => setKeyword(e.target.value)}
                    onKeyPress={handleKeyPress}
                />
                <button className="search-button" onClick={handleSearch}>
                    搜索
                </button>
            </div>

            {/* 标签切换区域 */}
            <div className="search-tabs">
                <div
                    className={`tab-item ${activeTab === 'all' ? 'active' : ''}`}
                    onClick={() => setActiveTab('all')}
                >
                    全部
                </div>
                <div
                    className={`tab-item ${activeTab === 'user' ? 'active' : ''}`}
                    onClick={() => setActiveTab('user')}
                >
                    用户
                </div>
                <div
                    className={`tab-item ${activeTab === 'code' ? 'active' : ''}`}
                    onClick={() => setActiveTab('code')}
                >
                    代码
                </div>
                <div
                    className={`tab-item ${activeTab === 'photo' ? 'active' : ''}`}
                    onClick={() => setActiveTab('photo')}
                >
                    图片
                </div>
                <div
                    className={`tab-item ${activeTab === 'novel' ? 'active' : ''}`}
                    onClick={() => setActiveTab('novel')}
                >
                    小说
                </div>
            </div>

            {/* 搜索结果区域 */}
            {renderResultContent()}
        </div>
    );
};

export default Search;