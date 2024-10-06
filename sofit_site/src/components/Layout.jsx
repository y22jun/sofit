import React from 'react';
import './Layout.scss';
import Header from './Header';
import Footer from './Footer';
import { Outlet } from "react-router-dom";

const Layout = () => {
    return (
        <div className='layout'>
            <Header/>
            <div className='content-wrapper'>
                <Outlet/>
            </div>
            <Footer/>
        </div>
    );
};

export default Layout;