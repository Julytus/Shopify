import React from 'react';
import { useOffCanvas } from '../offcanvas/OffCanvasContext';

const HeaderTools: React.FC = () => {
  const { openOffCanvas } = useOffCanvas();
  
  return (
    <div className="col">
      <div className="header-tools justify-content-end">
        <div className="header-login">
          <a href="my-account.html"><i className="far fa-user"></i></a>
        </div>
        <div className="header-search">
          <a onClick={() => openOffCanvas('search')} style={{ cursor: 'pointer' }}>
            <i className="fas fa-search"></i>
          </a>
        </div>
        <div className="header-wishlist">
          <a onClick={() => openOffCanvas('wishlist')} style={{ cursor: 'pointer' }}>
            <span className="wishlist-count">3</span>
            <i className="far fa-heart"></i>
          </a>
        </div>
        <div className="header-cart">
          <a onClick={() => openOffCanvas('cart')} style={{ cursor: 'pointer' }}>
            <span className="cart-count">3</span>
            <i className="fas fa-shopping-cart"></i>
          </a>
        </div>
      </div>
    </div>
  );
};

export default HeaderTools; 