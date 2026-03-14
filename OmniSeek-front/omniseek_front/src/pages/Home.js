import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Home.css';
import { searchAll } from "../utils/api";

function Home() {
    const navigate = useNavigate();
    const [result, setResult] = useState(null);
    const handleSearch = async () => {
        try {
            const  params = {
                keyword: '',
                type:["code"],
                current: 1,
                pageSize: 20
            }
            const response = await searchAll({

            });
            const data = await response.data;
            console.log(data);
        } catch (error) {
            console.error('搜索失败:', error);
        }
    };

    return (
        <div className="home">
            <header className="home-header">
                <h1>OmniSeek</h1>
                <p>Welcome to OmniSeek</p>
            </header>
            <main className="home-content">
                <button onClick={() => navigate('/search')} className="home-btn">
                    进入搜索
                </button>

                {/* 方案2：添加搜索按钮 */}
                <button onClick={handleSearch} className="home-btn">
                    测试搜索
                </button>
            </main>
        </div>
    );
}

export default Home;