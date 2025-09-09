-- phpMyAdmin SQL Dump
-- version 4.7.1
-- https://www.phpmyadmin.net/
--
-- Host: sql12.freesqldatabase.com
-- Generation Time: Sep 07, 2025 at 08:00 AM
-- Server version: 5.5.62-0ubuntu0.14.04.1
-- PHP Version: 7.0.33-0ubuntu0.16.04.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sql12796261`
--

-- --------------------------------------------------------

--
-- Table structure for table `Benh`
--

CREATE TABLE `Benh` (
  `ma_benh` int(11) NOT NULL,
  `ten_benh` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `mo_ta` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `ngay_tao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Benh`
--

INSERT INTO `Benh` (ma_benh, ten_benh, mo_ta, ngay_tao) VALUES
(1, 'Bệnh thương hàn', 'Thương hàn là 1 bệnh nhiễm khuẩn toàn thân do trực khuẩn Salmonella gây nên. Bệnh thường khởi phát đột ngột và có nhiều biến chứng nặng nề như xuất huyết tiêu hoá, thủng ruột, viêm cơ tim, viêm não … có thể dẫn đến tử vong. Tuy nhiên, cũng có trường hợp bệnh nhẹ có ít hoặc không có triệu chứng.', '2025-08-26 20:46:42'),
(2, 'Bệnh sởi', 'Sởi là loại bệnh truyền nhiễm cấp tính do virus gây ra có thể gặp ở nhiều đối tượng đặc biệt là trẻ em. Sởi rất dễ lây lan và bùng phát thành ổ dịch, mặc dù phần lớn trẻ mắc bệnh đều có thể hồi phục sau một thời gian nhưng ở những trẻ có sức đề kháng kém như trẻ nhũ nhi thì bệnh có thể diễn tiến nặng và gây ra biến chứng về sau.', '2025-08-26 20:46:42'),
(72, 'COVID-19', 'Bệnh do virus SARS-CoV-2 gây ra, gây sốt, ho, khó thở và có thể tử vong.', '2025-08-29 15:49:40'),
(73, 'Viêm gan B', 'Bệnh gan do virus viêm gan B, lây qua máu và dịch tiết cơ thể.', '2025-08-29 15:49:40'),
(74, 'Bạch hầu', 'Bệnh truyền nhiễm cấp tính, gây viêm họng, sốt, biến chứng tim và thần kinh.', '2025-08-29 15:49:40'),
(75, 'Ho gà', 'Bệnh nhiễm trùng đường hô hấp, gây ho kéo dài, nguy hiểm ở trẻ nhỏ.', '2025-08-29 15:49:40'),
(76, 'Uốn ván', 'Bệnh do vi khuẩn Clostridium tetani, gây co cứng cơ, nguy cơ tử vong cao.', '2025-08-29 15:49:40'),
(77, 'Bại liệt', 'Bệnh do virus poliomyelitis, gây liệt vĩnh viễn hoặc tạm thời.', '2025-08-29 15:49:40'),
(78, 'Sởi', 'Bệnh truyền nhiễm cấp tính, gây phát ban, sốt, ho, nguy cơ biến chứng cao.', '2025-08-29 15:49:40'),
(79, 'Quai bị', 'Bệnh virus gây sưng tuyến nước bọt, có thể ảnh hưởng đến tinh hoàn ở nam.', '2025-08-29 15:49:40'),
(80, 'Rubella', 'Bệnh phát ban nhẹ nhưng nguy hiểm cho phụ nữ mang thai.', '2025-08-29 15:49:40'),
(81, 'Thủy đậu', 'Bệnh virus gây phát ban, ngứa, có thể biến chứng viêm não.', '2025-08-29 15:49:40'),
(82, 'Viêm não Nhật Bản B', 'Bệnh do virus, gây viêm não, có thể tử vong hoặc di chứng thần kinh.', '2025-08-29 15:49:40'),
(83, 'Cúm A/H1N1', 'Bệnh do virus cúm A, gây sốt, mệt mỏi, ho và đau cơ.', '2025-08-29 15:49:40'),
(84, 'Cúm mùa', 'Bệnh cúm thông thường, gây sốt, mệt mỏi, ho, đau họng.', '2025-08-29 15:49:40'),
(85, 'Viêm gan A', 'Bệnh gan do virus viêm gan A, lây qua đường tiêu hóa.', '2025-08-29 15:49:40'),
(86, 'Phế cầu khuẩn', 'Nhiễm khuẩn đường hô hấp và huyết, có thể gây viêm màng não.', '2025-08-29 15:49:40'),
(87, 'Hib (Haemophilus influenzae type b)', 'Nhiễm khuẩn nguy hiểm ở trẻ nhỏ, gây viêm màng não, viêm phổi.', '2025-08-29 15:49:40'),
(88, 'Rotavirus', 'Gây tiêu chảy nặng ở trẻ em, nguy cơ mất nước cao.', '2025-08-29 15:49:40'),
(89, 'Thương hàn', 'Bệnh do vi khuẩn Salmonella Typhi, gây sốt kéo dài, tiêu chảy.', '2025-08-29 15:49:40'),
(90, 'Tả', 'Bệnh tiêu chảy cấp do vi khuẩn Vibrio cholerae.', '2025-08-29 15:49:40'),
(91, 'Dị ứng sữa', 'Phản ứng dị ứng do protein trong sữa, gây phát ban, nôn mửa.', '2025-08-29 15:49:40'),
(92, 'Sốt vàng da', 'Bệnh virus do muỗi truyền, gây sốt cao, vàng da, xuất huyết.', '2025-08-29 15:49:40'),
(93, 'Viêm phổi', 'Nhiễm trùng phổi do vi khuẩn hoặc virus, gây ho, sốt, khó thở.', '2025-08-29 15:49:40'),
(94, 'Sốt xuất huyết', 'Bệnh virus do muỗi Aedes truyền, gây sốt cao, đau cơ, xuất huyết.', '2025-08-29 15:49:40'),
(95, 'Thủy đậu biến chứng', 'Biến chứng của thủy đậu, có thể viêm não, viêm phổi.', '2025-08-29 15:49:40'),
(96, 'Virus HPV', 'Nguyên nhân chính gây ung thư cổ tử cung và bệnh sùi mào gà.', '2025-08-29 15:49:40'),
(97, 'Viêm gan E', 'Bệnh gan do virus viêm gan E, lây qua đường tiêu hóa, nguy hiểm cho phụ nữ mang thai.', '2025-08-29 15:49:40'),
(98, 'Mumps biến chứng', 'Biến chứng của quai bị, gây viêm tinh hoàn, viêm não.', '2025-08-29 15:49:40'),
(99, 'Rubella biến chứng', 'Nguy hiểm cho thai nhi khi mẹ mắc bệnh.', '2025-08-29 15:49:40'),
(100, 'Zona', 'Bệnh do virus thủy đậu tái hoạt, gây đau rát, phát ban.', '2025-08-29 15:49:40'),
(101, 'Cúm B', 'Gây sốt, mệt mỏi, ho, đau họng, chủ yếu ở trẻ em và người già.', '2025-08-29 15:49:40'),
(102, 'Tetanus sơ sinh', 'Uốn ván ở trẻ sơ sinh, nguy cơ tử vong cao.', '2025-08-29 15:49:40'),
(103, 'Viêm màng não do vi khuẩn', 'Gây viêm màng não, có thể tử vong hoặc di chứng thần kinh.', '2025-08-29 15:49:40'),
(104, 'Cúm H3N2', 'Virus cúm mùa khác, gây sốt, mệt mỏi, ho, đau cơ.', '2025-08-29 15:49:40'),
(105, 'Viêm gan D', 'Bệnh gan do virus viêm gan D, lây qua máu và quan hệ tình dục.', '2025-08-29 15:49:40'),
(106, 'Viêm phế quản', 'Nhiễm trùng đường hô hấp, gây ho, khó thở, nguy cơ viêm phổi.', '2025-08-29 15:49:40'),
(107, 'Virus Ebola', 'Bệnh do virus Ebola, gây sốt, xuất huyết, tỷ lệ tử vong cao.', '2025-08-29 15:49:40'),
(108, 'Virus Zika', 'Gây sốt, phát ban, nguy hiểm cho phụ nữ mang thai.', '2025-08-29 15:49:40'),
(109, 'Sốt chikungunya', 'Bệnh do muỗi truyền, gây sốt, đau khớp, phát ban.', '2025-08-29 15:49:40'),
(110, 'Coxsackie', 'Gây bệnh tay-chân-miệng, nổi mụn nước, sốt nhẹ.', '2025-08-29 15:49:40'),
(111, 'Hepatitis B mạn tính', 'Gây tổn thương gan lâu dài, nguy cơ xơ gan, ung thư gan.', '2025-08-29 15:49:40'),
(112, 'Varicella biến chứng', 'Biến chứng của thủy đậu, viêm phổi, viêm não.', '2025-08-29 15:49:40'),
(113, 'Viêm phổi do virus RSV', 'Gây viêm đường hô hấp dưới, phổ biến ở trẻ nhỏ, có thể nguy hiểm.', '2025-08-29 15:51:57'),
(114, 'Bệnh do Rotavirus biến chứng', 'Tiêu chảy nặng, mất nước, nguy cơ nhập viện cao ở trẻ em.', '2025-08-29 15:51:57'),
(115, 'Bệnh do Adenovirus', 'Gây sốt, viêm họng, viêm kết mạc, viêm phổi ở trẻ em.', '2025-08-29 15:51:57'),
(116, 'Viêm gan A cấp tính', 'Viêm gan A lây qua đường tiêu hóa, gây vàng da, mệt mỏi.', '2025-08-29 15:51:57'),
(117, 'Nhiễm khuẩn tụ cầu', 'Gây nhiễm trùng da, viêm mô mềm, nguy cơ nhiễm máu.', '2025-08-29 15:51:57'),
(118, 'Nhiễm khuẩn liên cầu', 'Gây viêm họng, viêm tai giữa, có thể biến chứng viêm cầu thận.', '2025-08-29 15:51:57'),
(119, 'Bệnh do Parainfluenza', 'Gây viêm đường hô hấp trên và dưới, ho, khò khè.', '2025-08-29 15:51:57'),
(120, 'Bệnh do Virus Nipah', 'Nguy hiểm, gây sốt, viêm não, tỷ lệ tử vong cao.', '2025-08-29 15:51:57'),
(121, 'Virus Hanta', 'Gây sốt, suy thận cấp, xuất huyết, lây từ động vật gặm nhấm.', '2025-08-29 15:51:57'),
(122, 'Virus Marburg', 'Nguy hiểm, gây sốt xuất huyết, tỷ lệ tử vong cao.', '2025-08-29 15:51:57');

-- --------------------------------------------------------

--
-- Table structure for table `Nguoi_Dung`
--

CREATE TABLE `Nguoi_Dung` (
  `ma_nguoi_dung` int(11) NOT NULL,
  `ho_ten` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ten_dang_nhap` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(100) NOT NULL,
  `mat_khau` varchar(255) NOT NULL,
  `vai_tro` enum('admin','bacsi','khach') DEFAULT 'khach',
  `ngay_tao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ngay_sinh` date DEFAULT NULL,
  `gioi_tinh` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Nguoi_Dung`
--

INSERT INTO `Nguoi_Dung` (ma_nguoi_dung, ho_ten, ten_dang_nhap, email, mat_khau, vai_tro, ngay_tao, ngay_sinh, gioi_tinh) VALUES
(1, 'Nguyễn Quang Phương', 'admin@example.com', 'admin@example.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'admin', '2025-08-26 20:51:06', '1990-01-01', 'Nam'),
(2, 'Nguyễn Minh Tâm', 'bacsiA@example.com', 'bacsiA@example.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'bacsi', '2025-08-26 20:51:06', '1985-05-15', 'Nam'),
(3, 'Nguyễn Thị Hồng Thủy', 'khachB@example.com', 'khachB@example.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'khach', '2025-08-26 20:51:06', '1995-08-20', 'Nữ'),
(101, 'Nguyễn Tấn Hải', 'BS Hải', 'bshai@example.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'bacsi', '2025-08-27 08:00:00', '1979-08-26', 'Nam'),
(102, 'Hồ Hồng Ngọc', 'BS Ngọc', 'bsngoc@example.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'bacsi', '2025-08-27 08:00:00', '1989-04-23', 'Nữ'),
(103, 'Nguyễn Thị Kim Loan', 'BS Loan', 'bsloan@example.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'bacsi', '2025-08-27 08:00:00', '1987-09-21', 'Nữ'),
(104, 'Hồ Trí Dũng', 'BS Dũng', 'bsdung@example.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'bacsi', '2025-08-27 08:00:00', '1990-12-12', 'Nam'),
(201, 'Đào Mộng Tuyền', 'tuyendao24', 'tuyendao24@example.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'khach', '2025-08-27 08:00:00', '2001-12-04', 'Nữ'),
(202, 'Dương Văn Toán', 'toan', 'toan1212@example.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'khach', '2025-08-27 08:00:00', '2001-12-12', 'Nam'),
(203, 'Trần Yến', 'sunflower', 'sunflower@example.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'khach', '2025-08-27 08:00:00', '2003-08-11', 'Nữ'),
(204, 'Thái Văn Hậu', 'thaivanhau', 'thaivanhau@example.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'khach', '2025-08-27 08:00:00', '1981-01-15', 'Khác'),
(205, 'Nguyễn Kiều', 'kieu_nguyen', 'kieu_nguyen@example.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'khach', '2025-08-27 08:00:00', '1999-04-29', 'Nữ'),
(206, 'Nguyễn Hồng Thúy', 'cataclysm', 'cataclysm@gmail.com', 'e46d36e8288ac0a77fa1af032e0db4284f2c0d464effc47c4132255d6afaaef7', 'khach', '2025-08-29 08:50:12', '1991-08-15', 'Nữ'),
(207, 'Xuan', 'Xuan', 'xuan@gmail.com', 'b34e1b486aa26bd5f0432128b50615734a23b0fcb1f5f9b074c049fb791123ca', 'khach', '2025-08-29 10:23:38', '2020-10-10', 'Nữ'),
(208, 'Tạ Thi Thơ', 'thi_tho', 'thi_tho@gmail.com', 'aa4e5dd0df6e2ce83b43879e194d512cff0bccff4804178a915be47ab0716d2b', 'khach', '2025-08-29 15:12:27', '2004-02-14', 'Nữ'),
(209, 'Nguyễn Trí Nhân', 'nhantri_123', 'nhantri_123@gmail.com', '15e2b0d3c33891ebb0f1ef609ec419420c20e320ce94c65fbc8c3312448eb225', 'khach', '2025-08-29 15:13:29', '1992-03-18', 'Khác'),
(210, 'Ngô Văn Hậu', 'ngo_hau_09', 'ngo_hau_09@gmail.com', '23707878f29af1981750a648c304c2223db73a866d490d1e236d9c277457a686', 'khach', '2025-08-29 15:14:19', '1994-02-02', 'Nam'),
(211, 'Lê Ngọc Trà', 'tra1234', 'tra1234@gmail.com', '1b037186d42db4fa3d6e4c14d760247f6dcdb9608dc9cb71afb346521e626712', 'khach', '2025-08-29 15:15:01', '2003-05-16', 'Nữ'),
(212, 'Lê Tú', 'tu_le_2006', 'tu_le_2006@gmail.com', '5a70b0aa90df15fe320482b585cc3d6a3fc024310b2565b32e1aed9d8df51d39', 'khach', '2025-08-29 15:16:11', '2006-09-10', 'Nữ'),
(213, 'Hoàng Ngọc Hà', 'ha_hoang', 'ha_hoang@gmail.com', 'c6e0956dd6b18cc141fb2bdd41b2b42f721cf0a4c3fa55e7426400b6cfbbfbe4', 'khach', '2025-08-29 15:17:11', '1986-03-24', 'Nữ'),
(214, 'Thái Quang', 'quang_112', 'quang_112@gmail.com', '1095a683bc746f440da3f2212e1ac9fcbe60eb4244dfc8b77060a79b5bbc1db5', 'khach', '2025-08-29 15:18:08', '2007-05-23', 'Khác'),
(215, 'Hồ Ngọc Yến', 'yen_ngoc', 'yen_ngoc12@gmail.com', '3bfb3883322679abb92cbd77feae64a65f87a45b2710dd91b64d5840134f759e', 'khach', '2025-08-29 15:18:57', '1991-08-29', 'Nữ'),
(216, 'Nguyễn Thị Kim', 'kimthi', 'kim_thi@gmail.com', '696f3b1f55d163b17e11f1eb8aa345145265211b4a909d0d528553b0784ad32d', 'khach', '2025-08-29 15:20:50', '2003-07-08', 'Nữ'),
(217, 'Nguyễn Thanh Thơ', 'thanh_tho_34', 'thanh_tho_34@gmail.com', 'c7e3ef93dd4cfc25bf05a73644210dcba42182c799a75cf4b21ed7b24145ee88', 'khach', '2025-08-29 15:24:46', '1995-12-06', 'Nữ'),
(218, 'Nguyễn Hậu', 'hau_nguyen', 'hau_nguyen@gmail.com', '23707878f29af1981750a648c304c2223db73a866d490d1e236d9c277457a686', 'khach', '2025-08-29 15:25:47', '2001-03-06', 'Nam'),
(220, 'Nguyễn Thị Hồng Ngọc', 'hong_ngoc_13', 'hong_ngoc_13@gmail.com', 'f5759f41a247ce9cf49182a80eb799c66aa4bda53ade04f795c7f96d1fdaaa6e', 'khach', '2025-08-29 15:27:17', '1994-08-29', 'Nữ'),
(221, 'Trần Thị Thanh Thúy', 'thanh_thuy', 'thanh_thuy@gmail.com', '9dfb8592a68ec0e4630da50b3f475d2993ab6880a5e2616c053945923ec3de1b', 'khach', '2025-08-29 15:27:56', '1999-06-29', 'Nữ'),
(222, 'Đào Kha', 'dao_kha', 'dao_kha@gmail.com', '695428ee0d755616e513bdebbbf5d0eca1a8d8cc5be6a3da60fc40ab0a83bce7', 'khach', '2025-08-29 15:28:41', '2000-05-07', 'Khác'),
(223, 'Hồ Bích', 'ho_bich', 'ho_bich@gmail.com', '932655836fc577cb8699f51d97de4d4dba8c811bea0d89b38af371a850f53913', 'khach', '2025-08-29 15:29:14', '1993-09-09', 'Nữ'),
(224, 'Lê Tuyền', 'tuyen_le', 'tuyen_le@gmail.com', '8af4d3f3f405a8573a304cc6817e4533c1b3d97213acf6df97d7a19bb674bbc3', 'khach', '2025-08-29 15:30:41', '1887-02-15', 'Nữ'),
(225, 'Nguyễn Trung Tính', 'trung_tinh', 'trung_tinh@gmail.com', '3edbfd2f0bf6f48faab21f87c6b1a70e4a3bf8e68bdbee5ecf136a398cb926f2', 'khach', '2025-08-29 15:31:38', '1886-12-04', 'Nam'),
(228, 'Tô Phong', 'to_phong_2002', 'to_phong_2002@gmail.com', '90438e6aedc0b52995b6fede2945a8d7786ce6855e982962a88e655760b15db7', 'khach', '2025-08-29 15:33:57', '2002-06-17', 'Nam'),
(229, 'Trần Hải Vân', 'haivan', 'haivan13@gmail.com', '8c3fa6fe8c3b7838dda4a01da61957de302f72e1d9752835fb5db8acf1b96608', 'khach', '2025-08-29 15:34:46', '1991-03-13', 'Nữ'),
(230, 'Nguyễn Trâm Anh', 'tram_anh_11', 'tram_anh_11@gmail.com', 'b00f9aec6509c9e32870041fe9c152c378403620b91a8719f07aaed724212e66', 'khach', '2025-08-29 15:36:42', '1995-04-30', 'Nữ'),
(231, 'Nguyễn Thị Đại', 'daihongthuy', 'daihongthuy@gmail.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'khach', '2025-08-29 17:05:17', '1991-08-15', 'Nữ'),
(232, 'Trần Thị Đào', 'admintest@example.com', 'admintest@example.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'admin', '2025-08-26 20:51:06', '1990-01-01', 'Nữ'),
(235, 'Ngô Minh', 'ngo_minh123', 'ngo_minh@gmail.com', '61be55a8e2f6b4e172338bddf184d6dbee29c98853e0a0485ecee7f27b9af0b4', 'khach', '2025-09-04 14:37:41', '2000-01-01', 'Nam'),
(238, 'Quốc Niên', 'quoc_nien_111', 'quoc_nien_111@gmail.com', 'aaa', 'admin', '2025-09-04 22:27:03', '2000-01-01', 'Nam');

-- --------------------------------------------------------

--
-- Table structure for table `Nha_San_Xuat`
--

CREATE TABLE `Nha_San_Xuat` (
  `ma_nha_sx` int(11) NOT NULL,
  `ten_nha_sx` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `quoc_gia` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ngay_tao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Nha_San_Xuat`
--

INSERT INTO `Nha_San_Xuat` (ma_nha_sx, ten_nha_sx, quoc_gia, ngay_tao) VALUES
(1, 'Sanofi Pasteur SA', 'Việt Nam', '2025-08-26 20:46:44'),
(3, 'Sanofi Pasteur', 'Pháp', '2025-08-29 15:57:35'),
(4, 'GSK (GlaxoSmithKline)', 'Anh', '2025-08-29 15:57:35'),
(5, 'Merck & Co.', 'Mỹ', '2025-08-29 15:57:35'),
(6, 'Pfizer', 'Mỹ', '2025-08-29 15:57:35'),
(7, 'AstraZeneca', 'Anh', '2025-08-29 15:57:35'),
(8, 'Johnson & Johnson', 'Mỹ', '2025-08-29 15:57:35'),
(9, 'Moderna', 'Mỹ', '2025-08-29 15:57:35'),
(10, 'Sinovac Biotech', 'Trung Quốc', '2025-08-29 15:57:35'),
(11, 'Sinopharm', 'Trung Quốc', '2025-08-29 15:57:35'),
(12, 'Bavarian Nordic', 'Đức', '2025-08-29 15:57:35'),
(13, 'Bharat Biotech', 'Ấn Độ', '2025-08-29 15:57:35'),
(14, 'Serum Institute of India', 'Ấn Độ', '2025-08-29 15:57:35'),
(15, 'Chumakov Institute', 'Nga', '2025-08-29 15:57:35'),
(16, 'Institut Pasteur', 'Pháp', '2025-08-29 15:57:35'),
(17, 'Novavax', 'Mỹ', '2025-08-29 15:57:35'),
(18, 'BioNTech', 'Đức', '2025-08-29 15:57:35'),
(19, 'Vabiotech', 'Việt Nam', '2025-08-29 15:57:35'),
(20, 'Polyvac', 'Việt Nam', '2025-08-29 15:57:35'),
(21, 'IVAC', 'Việt Nam', '2025-08-29 15:57:35'),
(22, 'Nanogen', 'Việt Nam', '2025-08-29 15:57:35'),
(23, 'Imexpharm', 'Việt Nam', '2025-08-29 15:57:35'),
(24, 'Green Cross', 'Hàn Quốc', '2025-08-29 15:57:35'),
(25, 'Shenzhen Kangtai Biological Products', 'Trung Quốc', '2025-08-29 15:57:35'),
(26, 'CanSino Biologics', 'Trung Quốc', '2025-08-29 16:00:15'),
(27, 'Valneva', 'Pháp', '2025-08-29 16:00:15'),
(28, 'Vaxzevria', 'Anh', '2025-08-29 16:00:15'),
(29, 'Covaxin', 'Ấn Độ', '2025-08-29 16:00:15'),
(30, 'Covovax', 'Ấn Độ', '2025-08-29 16:00:15'),
(31, 'Covilo', 'Trung Quốc', '2025-08-29 16:00:15'),
(32, 'Hayat-Vax', 'UAE', '2025-08-29 16:00:15'),
(33, 'Sputnik V', 'Nga', '2025-08-29 16:00:15'),
(34, 'EpiVacCorona', 'Nga', '2025-08-29 16:00:15'),
(35, 'BBIBP-CorV', 'Trung Quốc', '2025-08-29 16:00:15'),
(36, 'CoronaVac', 'Trung Quốc', '2025-08-29 16:00:15'),
(37, 'Serum Institute of India', 'Ấn Độ', '2025-08-29 16:00:15'),
(38, 'Bharat Biotech', 'Ấn Độ', '2025-08-29 16:00:15'),
(39, 'Chumakov Institute', 'Nga', '2025-08-29 16:00:15'),
(40, 'Novavax', 'Mỹ', '2025-08-29 16:00:15'),
(41, 'BioNTech', 'Đức', '2025-08-29 16:00:15'),
(42, 'Green Cross', 'Hàn Quốc', '2025-08-29 16:00:15'),
(43, 'Shenzhen Kangtai Biological Products', 'Trung Quốc', '2025-08-29 16:00:15'),
(44, 'Fujirebio', 'Nhật Bản', '2025-08-29 16:00:15'),
(45, 'Takeda Pharmaceutical', 'Nhật Bản', '2025-08-29 16:00:15'),
(46, 'Mitsubishi Tanabe Pharma', 'Nhật Bản', '2025-08-29 16:00:15'),
(47, 'Changchun BCHT Biotechnology', 'Trung Quốc', '2025-08-29 16:00:15'),
(48, 'Wuhan Institute of Biological Products', 'Trung Quốc', '2025-08-29 16:00:15'),
(49, 'Biological E.', 'Ấn Độ', '2025-08-29 16:00:15'),
(50, 'SK Bioscience', 'Hàn Quốc', '2025-08-29 16:00:15');

-- --------------------------------------------------------

--
-- Table structure for table `Tiem_Chung`
--

CREATE TABLE `Tiem_Chung` (
  `ma_tiem_chung` int(11) NOT NULL,
  `ma_vaccine` int(11) DEFAULT NULL,
  `ma_bac_si` int(11) DEFAULT NULL,
  `ma_khach` int(11) DEFAULT NULL,
  `ngay_chi_dinh` date DEFAULT NULL,
  `ngay_tiem` date DEFAULT NULL,
  `trang_thai_tiem` enum('da_tiem','huy','cho_tiem') COLLATE utf8mb4_unicode_ci DEFAULT 'cho_tiem',
  `ghi_chu` text COLLATE utf8mb4_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `Tiem_Chung`
--

INSERT INTO Tiem_Chung (ma_tiem_chung, ma_vaccine, ma_bac_si, ma_khach, ngay_chi_dinh, ngay_tiem, trang_thai_tiem, ghi_chu) VALUES
(3, 21, 2, 202, '2025-09-05', '2024-04-13', 'cho_tiem', 'Bệnh nhân không sốt, không bệnh 1 tuần trước khi tiêm.'),
(4, 20, 2, 215, '2025-09-05', '2025-06-07', 'cho_tiem', 'Bệnh nhân không sốt, không bệnh.'),
(5, 11, 2, 3, '2025-09-05', '2025-08-23', 'da_tiem', 'Bệnh nhân đã làm xét nghiệm kháng thể.'),
(6, 32, 2, 230, '2025-09-05', '2025-07-23', 'cho_tiem', 'Bệnh nhân đã từng bị sốt xuất 2 lần, tình trạng lúc tiêm không sốt.'),
(7, 2, 2, 3, '2025-09-05', '2020-05-25', 'cho_tiem', 'Mũi 1'),
(8, 3, 2, 3, '2025-09-05', '2020-07-23', 'da_tiem', 'Mũi 2'),
(9, 36, 2, 3, '2025-09-05', '2025-02-04', 'cho_tiem', 'Bệnh nhân sức khỏe ổn định, không sốt.'),
(11, 20, 2, 3, '2025-09-06', '2025-08-12', 'cho_tiem', 'Mũi 1'),
(12, 1, 2, 3, '2025-09-06', '2025-04-28', 'huy', 'Bệnh nhân đang dùng thuốc điều trị.'),
(13, 33, 2, 201, '2025-09-07', '2020-05-12', 'da_tiem', 'Mũi 1'),
(14, 33, 2, 201, '2025-09-07', '2021-04-23', 'da_tiem', 'Mũi 2'),
(15, 18, 2, 229, '2025-09-07', '2023-10-23', 'da_tiem', 'Bệnh nhân không sốt, cần theo dõi kỹ sau tiêm do đã từng sốc phản vệ.'),
(16, 20, 2, 211, '2025-09-07', '2019-09-17', 'da_tiem', 'Bệnh nhân không sốt.'),
(17, 30, 2, 220, '2025-09-07', '2023-09-25', 'da_tiem', 'Bệnh nhân không sốt.'),
(18, 15, 2, 231, '2025-09-07', '2022-09-27', 'huy', 'Bệnh nhân sốt nhẹ.'),
(19, 1, 2, 217, '2025-09-07', '2024-09-09', 'huy', 'Bệnh nhân sốt nhẹ.');

-- --------------------------------------------------------

--
-- Table structure for table `Vaccine`
--

CREATE TABLE `Vaccine` (
  `ma_vaccine` int(11) NOT NULL,
  `ten_vaccine` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `hinh_anh_url` varchar(255) DEFAULT NULL,
  `so_lo` varchar(50) DEFAULT NULL,
  `ngay_sx` date DEFAULT NULL,
  `ngay_het_han` date DEFAULT NULL,
  `tong_sl` int(11) DEFAULT NULL,
  `sl_con_lai` int(11) DEFAULT NULL,
  `gia_nhap` decimal(10,2) DEFAULT NULL,
  `gia_ban` decimal(10,2) DEFAULT NULL,
  `mo_ta` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `ma_nha_sx` int(11) DEFAULT NULL,
  `ngay_tao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ma_benh` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Vaccine`
--

INSERT INTO `Vaccine` (ma_vaccine, ten_vaccine, hinh_anh_url, so_lo, ngay_sx, ngay_het_han, tong_sl, sl_con_lai, gia_nhap, gia_ban, mo_ta, ma_nha_sx, ngay_tao, ma_benh) VALUES
(1, 'Pfizer Covid-12', 'https://file3.qdnd.vn/data/images/0/2021/11/03/lamanh/3c67765bde3f1f251b77863acb0cfad83855150pm.jpeg?dpi=150&quality=100&w=870', 'PFZ1222', '2025-01-01', '2026-01-01', 109922, 8089, '10220.00', '2202288.00', 'Vắc xin phòng ngừa virus gây bệnh Covid-19', 18, '2025-09-07 15:27:06', 72),
(2, 'Moderna Covid-19', 'https://www.aljazeera.com/wp-content/uploads/2020/11/2020-11-16T121400Z_529170601_RC2C4K9YQKBE_RTRMADP_3_HEALTH-CORONAVIRUS-VACCINES-MODERNA.jpg?resize=1920%2C1440', 'MDN456', '2025-02-01', '2026-02-01', 900, 749, '210000.00', '260000.00', 'Vaccine ph', 1, '2025-09-07 15:27:15', 72),
(3, 'AstraZeneca Covid-19', 'https://cdn.nhandan.vn/images/a66ff20f6c1e5a179e007244f7bfd5c8b3de07804d87f688ce5751ff9eed2f0d50a346fbadcc4db4e90922e5f3275e3383285454b4f04c7090dc1a4f800ddf14/8714-777jpg-307.jpg', 'AZ789', '2025-03-01', '2026-03-01', 1200, 999, '180000.00', '230000.00', 'Vaccine phòng ngừa Covid-19.', 7, '2025-09-07 15:27:25', 72),
(5, 'Vaccine COVID-19 Moderna', 'https://images.baodantoc.vn/uploads/2021/Th%C3%A1ng%206/Ng%C3%A0y_29/Thanh/vaccine-covid19-moderna-vimedimex.jpg', 'M001', '2025-01-15', '2027-01-14', 1000, 1000, '200000.00', '300000.00', 'Vaccine phòng COVID-19, 2 mũi.', 7, '2025-09-07 15:27:32', 72),
(6, 'Vaccine COVID-19 Pfizer', 'http://cdc.ninhbinh.gov.vn/upload/100765/20210924/vac-xin-pfizer-lieu-thap-an-toan-va-hieu-qua-voi-tre-5-11-tuoi_bc88c.jpg', 'P001', '2025-02-01', '2027-02-01', 1200, 1200, '210000.00', '310000.00', 'Vaccine phòng COVID-19, 2 mũi.', 41, '2025-09-07 15:27:46', 72),
(7, 'Vaccine COVID-19 AstraZeneca', 'https://cdnmedia.baotintuc.vn/Upload/a7srThwxbojBCucvUWgnxA/files/2024/05/08/AstraZeneca-08052024.jpg', 'A001', '2025-01-20', '2027-01-19', 800, 800, '180000.00', '280000.00', 'Vaccine phòng COVID-19, 2 mũi.', 7, '2025-09-07 15:31:22', 72),
(8, 'Vaccine COVID-19 Johnson & Johnson', 'https://media.vneconomy.vn/images/upload/2021/10/02/000-9927zz.jpg', 'JJ001', '2025-03-01', '2027-02-28', 900, 900, '190000.00', '290000.00', 'Vaccine COVID-19 liều duy nhất.', 8, '2025-09-07 15:31:30', 72),
(9, 'Vaccine COVID-19 Sinovac', 'https://www.uc.cl/site/assets/files/14801/coronavac_700x532.700x532.jpg', 'S001', '2025-03-05', '2027-03-04', 1000, 1000, '190000.00', '290000.00', 'Vaccine phòng COVID-19 Sinovac.', 10, '2025-09-07 15:31:40', 72),
(10, 'Vaccine COVID-19 Sinopharm', 'https://bcp.cdnchinhphu.vn/thumb_w/777/Uploaded/dangdinhnam/2021_06_20/vac%20xin%20Sinopharm.jpg', 'S002', '2025-03-05', '2027-03-04', 1000, 1000, '190000.00', '290000.00', 'Vaccine phòng COVID-19 Sinopharm.', 11, '2025-09-07 15:31:47', 72),
(11, 'Vaccine Viêm gan B', 'https://cdn.tiemchunglongchau.com.vn/unsafe/600x600/DSC_04562_a6e4fff224.jpg', 'HB001', '2024-12-01', '2026-11-30', 1000, 999, '120000.00', '200000.00', 'Vaccine phòng viêm gan B.', 3, '2025-09-07 15:31:58', 73),
(12, 'Vaccine Viêm gan A', 'https://cdn.tiemchunglongchau.com.vn/unsafe/600x600/DSC_04621_6cf4e83447.jpg', 'HA001', '2024-12-05', '2026-12-04', 800, 800, '100000.00', '180000.00', 'Vaccine phòng viêm gan A.', 3, '2025-09-07 15:32:09', 85),
(13, 'Vaccine Bạch hầu', 'https://cdn.tiemchunglongchau.com.vn/unsafe/600x600/DSC_00701_e314b7c030.jpg', 'D001', '2024-10-10', '2026-10-09', 600, 600, '140000.00', '230000.00', 'Vaccine phòng bệnh bạch hầu.', 4, '2025-09-07 15:32:29', 74),
(14, 'Vaccine Ho gà', 'https://cdn.tiemchunglongchau.com.vn/unsafe/600x600/DSC_00701_e314b7c030.jpg', 'P002', '2024-10-12', '2026-10-11', 600, 600, '140000.00', '230000.00', 'Vaccine phòng bệnh ho gà.', 4, '2025-09-07 15:32:37', 75),
(15, 'Vaccine Uốn ván', 'https://cdn.tiemchunglongchau.com.vn/unsafe/600x600/DSC_00701_e314b7c030.jpg', 'T001', '2024-10-01', '2026-09-30', 700, 699, '140000.00', '240000.00', 'Vaccine phòng bệnh uốn ván.', 4, '2025-09-07 15:32:47', 76),
(16, 'Vaccine Bại liệt', 'https://www.biofarma.co.id/media/image/thumbs/post/2020/12/08/bivalen-type-1-3-oral-poliomyelitis-vaccines-550-350_thumb_c.jpg', 'PL001', '2024-08-01', '2026-07-31', 800, 800, '140000.00', '240000.00', 'Vaccine phòng bệnh bại liệt.', 35, '2025-09-07 15:32:59', 77),
(17, 'Vaccine Sởi', 'https://cdn.tiemchunglongchau.com.vn/unsafe/600x600/DSC_04378_edf5b53be9.jpg', 'S002', '2024-11-10', '2026-11-09', 500, 500, '150000.00', '250000.00', 'Vaccine phòng bệnh sởi.', 4, '2025-09-07 15:33:06', 2),
(18, 'Vaccine Quai bị', 'https://cdn.tiemchunglongchau.com.vn/unsafe/600x600/DSC_04378_edf5b53be9.jpg', 'Q001', '2024-11-12', '2026-11-11', 500, 499, '150000.00', '250000.00', 'Vaccine phòng bệnh quai bị.', 4, '2025-09-07 15:33:25', 79),
(19, 'Vaccine Rubella', 'https://cdn.tiemchunglongchau.com.vn/unsafe/600x600/DSC_04378_edf5b53be9.jpg', 'R001', '2024-11-15', '2026-11-14', 500, 500, '150000.00', '250000.00', 'Vaccine phòng bệnh rubella.', 4, '2025-09-07 15:33:38', 80),
(20, 'Vaccine Thủy đậu', 'https://cdn.tiemchunglongchau.com.vn/unsafe/600x600/DSC_00730_d20593165f.jpg', 'V001', '2024-07-15', '2026-07-14', 400, 397, '130000.00', '220000.00', 'Vaccine phòng bệnh thủy đậu.', 4, '2025-09-07 15:33:46', 81),
(21, 'Vaccine Viêm não Nhật Bản B', 'https://cdn.tiemchunglongchau.com.vn/unsafe/600x600/DSC_08042_b5878a3d63.jpg', 'JE001', '2025-01-10', '2027-01-09', 600, 599, '210000.00', '310000.00', 'Vaccine phòng viêm não Nhật Bản B.', 49, '2025-09-07 15:33:54', 82),
(22, 'Vaccine Cúm A/H1N1', 'https://tienthangvet.vn/wp-content/uploads/Vac-xin-dong-kho-phong-benh-tai-xanh-chung-jxa-r-600x600.jpg', 'F001', '2024-09-01', '2025-08-31', 1500, 1500, '90000.00', '150000.00', 'Vaccine phòng cúm A/H1N1.', 48, '2025-09-07 15:19:56', 83),
(23, 'Vaccine Cúm mùa', 'https://cdn.tiemchunglongchau.com.vn/unsafe/600x600/DSC_08800_5250a19a35.jpg', 'F002', '2024-09-05', '2025-09-04', 1200, 1200, '95000.00', '155000.00', 'Vaccine phòng cúm mùa.', 3, '2025-09-07 15:20:05', 84),
(24, 'Vaccine Phế cầu khuẩn', 'https://bvndtp.org.vn/wp-content/uploads/2022/06/phe-cau.jpg', 'PC001', '2024-09-15', '2026-09-14', 800, 800, '160000.00', '260000.00', 'Vaccine phòng viêm phổi do phế cầu khuẩn.', 12, '2025-09-07 15:20:16', 86),
(25, 'Vaccine Hib', 'https://cdn.tiemchunglongchau.com.vn/unsafe/600x600/quimi_mib_64c493edef.jpg', 'H001', '2024-08-20', '2026-08-19', 700, 700, '150000.00', '240000.00', 'Vaccine phòng Hib (Haemophilus influenzae type b).', 11, '2025-09-07 15:20:36', 87),
(26, 'Vaccine Rotavirus', 'https://cdn.tiemchunglongchau.com.vn/unsafe/600x600/DSC_05576_0b2debfdbe.jpg', 'R002', '2024-07-01', '2026-06-30', 600, 600, '140000.00', '230000.00', 'Vaccine phòng bệnh tiêu chảy do Rotavirus.', 5, '2025-09-07 15:20:55', 88),
(27, 'Vaccine Thương hàn', 'https://example.com/typhoid.jpg', 'TH001', '2024-09-10', '2026-09-09', 600, 600, '130000.00', '220000.00', 'Vaccine phòng bệnh thương hàn.', 15, '2025-09-07 15:21:06', 1),
(28, 'Vaccine Tả', 'https://example.com/cholera.jpg', 'CH001', '2024-09-15', '2026-09-14', 500, 500, '120000.00', '210000.00', 'Vaccine phòng bệnh tả.', 16, '2025-09-07 15:21:18', 90),
(29, 'Vaccine Dị ứng sữa', 'https://example.com/milkallergy.jpg', 'DA001', '2024-08-05', '2026-08-04', 400, 400, '110000.00', '200000.00', 'Vaccine phòng dị ứng sữa.', 17, '2025-09-07 15:21:30', 91),
(30, 'Vaccine Sốt vàng da', 'https://example.com/yellowfever.jpg', 'YF001', '2024-07-20', '2026-07-19', 450, 449, '180000.00', '280000.00', 'Vaccine phòng sốt vàng da.', 18, '2025-09-07 15:21:42', 92),
(31, 'Vaccine Viêm phổi', 'https://example.com/pneumonia2.jpg', 'VP001', '2024-09-25', '2026-09-24', 500, 500, '160000.00', '260000.00', 'Vaccine phòng viêm phổi.', 19, '2025-09-07 15:21:54', 93),
(32, 'Vaccine Sốt xuất huyết', 'https://example.com/dengue.jpg', 'D001', '2024-08-15', '2026-08-14', 700, 699, '190000.00', '300000.00', 'Vaccine phòng sốt xuất huyết.', 20, '2025-09-07 15:22:01', 94),
(33, 'Vaccine Virus HPV', 'https://example.com/hpv.jpg', 'HPV001', '2024-08-01', '2027-07-31', 500, 498, '250000.00', '400000.00', 'Vaccine phòng virus HPV.', 24, '2025-09-07 15:22:12', 96),
(34, 'Vaccine Viêm gan E', 'https://example.com/hepe.jpg', 'HE001', '2024-12-20', '2026-12-19', 400, 400, '150000.00', '250000.00', 'Vaccine phòng viêm gan E.', 3, '2025-09-07 15:22:22', 97),
(35, 'Vaccine Zona', 'https://example.com/shingles.jpg', 'Z001', '2024-07-30', '2026-07-29', 300, 300, '180000.00', '280000.00', 'Vaccine phòng bệnh Zona.', 12, '2025-09-07 15:22:36', 100),
(36, 'Vaccine Cúm B', 'https://example.com/flub.jpg', 'F003', '2024-09-10', '2026-09-09', 500, 499, '95000.00', '155000.00', 'Vaccine phòng cúm B.', 3, '2025-09-07 15:22:47', 101),
(37, 'Vaccine Tetanus sơ sinh', 'https://example.com/tetnewborn.jpg', 'TN001', '2024-10-05', '2026-10-04', 400, 400, '140000.00', '240000.00', 'Vaccine phòng uốn ván sơ sinh.', 3, '2025-09-07 15:23:01', 102),
(38, 'Vaccine Viêm màng não do vi khuẩn', 'https://example.com/meningitis.jpg', 'MN001', '2025-02-10', '2027-02-09', 450, 450, '200000.00', '300000.00', 'Vaccine phòng viêm màng não do vi khuẩn.', 11, '2025-09-07 15:23:11', 103),
(39, 'Vaccine Cúm H3N2', 'https://example.com/h3n2.jpg', 'F004', '2024-09-15', '2026-09-14', 400, 400, '95000.00', '155000.00', 'Vaccine phòng cúm H3N2.', 3, '2025-09-07 15:23:23', 104),
(40, 'Vaccine Viêm gan D', 'https://example.com/hepd.jpg', 'HD001', '2024-12-25', '2026-12-24', 300, 300, '150000.00', '250000.00', 'Vaccine phòng viêm gan D.', 3, '2025-09-07 15:23:32', 105),
(41, 'Vaccine Viêm phế quản', 'https://example.com/bronchitis.jpg', 'BP001', '2024-09-20', '2026-09-19', 350, 350, '140000.00', '240000.00', 'Vaccine phòng viêm phế quản.', 3, '2025-09-07 15:25:26', 106),
(42, 'Vaccine Virus Ebola', 'https://example.com/ebola.jpg', 'EBO001', '2025-01-05', '2027-01-04', 200, 200, '300000.00', '450000.00', 'Vaccine phòng virus Ebola.', 3, '2025-09-07 15:25:40', 107),
(43, 'Vaccine Virus Zika', 'https://example.com/zika.jpg', 'ZIKA001', '2025-01-10', '2027-01-09', 250, 250, '200000.00', '350000.00', 'Vaccine phòng virus Zika.', 3, '2025-09-07 15:25:47', 108),
(44, 'Vaccine Sốt chikungunya', 'https://example.com/chikungunya.jpg', 'CHK001', '2025-01-15', '2027-01-14', 200, 200, '180000.00', '300000.00', 'Vaccine phòng sốt chikungunya.', 3, '2025-09-07 15:25:56', 109),
(46, 'Vaccine Hepatitis B mạn tính', 'https://example.com/hepbchronic.jpg', 'HBCH001', '2024-12-10', '2026-12-09', 300, 300, '150000.00', '250000.00', 'Vaccine phòng viêm gan B mạn tính.', 3, '2025-09-07 15:26:16', 111),
(47, 'Vaccine Varicella biến chứng', 'https://example.com/varicella.jpg', 'VBC001', '2024-07-20', '2026-07-19', 2500, 250, '130000.00', '220000.00', 'Vaccine phòng biến chứng thủy đậu.', 4, '2025-09-07 15:26:31', 112);

--
-- Indexes for table `Benh`
--
ALTER TABLE `Benh`
  ADD PRIMARY KEY (`ma_benh`);

--
-- Indexes for table `Nguoi_Dung`
--
ALTER TABLE `Nguoi_Dung`
  ADD PRIMARY KEY (`ma_nguoi_dung`),
  ADD UNIQUE KEY `ten_dang_nhap` (`ten_dang_nhap`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `Nha_San_Xuat`
--
ALTER TABLE `Nha_San_Xuat`
  ADD PRIMARY KEY (`ma_nha_sx`);

--
-- Indexes for table `Tiem_Chung`
--
ALTER TABLE `Tiem_Chung`
  ADD PRIMARY KEY (`ma_tiem_chung`),
  ADD KEY `ma_vaccine` (`ma_vaccine`),
  ADD KEY `ma_bac_si` (`ma_bac_si`),
  ADD KEY `ma_khach` (`ma_khach`);

--
-- Indexes for table `Vaccine`
--
ALTER TABLE `Vaccine`
  ADD PRIMARY KEY (`ma_vaccine`),
  ADD KEY `ma_nha_sx` (`ma_nha_sx`),
  ADD KEY `fk_vaccine_benh` (`ma_benh`);


--
-- AUTO_INCREMENT for table `Benh`
--
ALTER TABLE `Benh`
  MODIFY `ma_benh` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=123;
--
-- AUTO_INCREMENT for table `Nguoi_Dung`
--
ALTER TABLE `Nguoi_Dung`
  MODIFY `ma_nguoi_dung` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=239;
--
-- AUTO_INCREMENT for table `Nha_San_Xuat`
--
ALTER TABLE `Nha_San_Xuat`
  MODIFY `ma_nha_sx` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=54;
--
-- AUTO_INCREMENT for table `Tiem_Chung`
--
ALTER TABLE `Tiem_Chung`
  MODIFY `ma_tiem_chung` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
--
-- AUTO_INCREMENT for table `Vaccine`
--
ALTER TABLE `Vaccine`
  MODIFY `ma_vaccine` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;


--
-- Constraints for table `Tiem_Chung`
--
ALTER TABLE `Tiem_Chung`
  ADD CONSTRAINT `Tiem_Chung_ibfk_1` FOREIGN KEY (`ma_vaccine`) REFERENCES `Vaccine` (`ma_vaccine`),
  ADD CONSTRAINT `Tiem_Chung_ibfk_2` FOREIGN KEY (`ma_bac_si`) REFERENCES `Nguoi_Dung` (`ma_nguoi_dung`),
  ADD CONSTRAINT `Tiem_Chung_ibfk_3` FOREIGN KEY (`ma_khach`) REFERENCES `Nguoi_Dung` (`ma_nguoi_dung`);

--
-- Constraints for table `Vaccine`
--
ALTER TABLE `Vaccine`
  ADD CONSTRAINT `fk_vaccine_benh` FOREIGN KEY (`ma_benh`) REFERENCES `Benh` (`ma_benh`),
  ADD CONSTRAINT `Vaccine_ibfk_2` FOREIGN KEY (`ma_nha_sx`) REFERENCES `Nha_San_Xuat` (`ma_nha_sx`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
