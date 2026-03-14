
import axios from 'axios';

// 基础配置
const axiosInstance = axios.create({
    baseURL: 'http://localhost:8080', // 根据实际后端地址调整
    timeout: 10000,
    headers: {
        'Content-Type': 'application/json',
    },
});

/**
 * 全量搜索接口（适配后端POST + RequestBody）
 * @param {Object} params - 搜索参数
 * @param {string} params.keyword - 搜索关键字（为空时返回所有）
 * @param {Array<string>} params.types - 搜索类型列表 ['user', 'code', 'photo']
 * @returns {Promise} 响应结果
 */
export const searchAll = (params) => {
    // 构造后端需要的SearchRequest结构
    const searchRequest = {
        keyword: params.keyword || '',
        type: params.types || ['user', 'code', 'photo'], // 默认搜索所有类型
        // 如需分页可补充：pageNum: 1, pageSize: 20（根据后端实际参数调整）
    };

    return axiosInstance.post('/search/all', searchRequest);
};

// 备用：各类型单独查询接口（根据OpenAPI补充）
export const getUserById = (id) => axiosInstance.get(`/user/get/${id}`);
export const getCodeById = (id) => axiosInstance.get(`/code/get/${id}`);
export const getPhotoById = (id) => axiosInstance.get(`/photo/get/${id}`);