
<div id="banner">
    <img id="CompanyLogo" src="../media/images/tree-logo.jpg" alt="CompanyLogo">
    <p id="CompanyName"><?php echo $companyName; ?></p>
</div>
<nav id="WebMainNav">
    <ul>
        <a href="../index.php">
        <li class="<?= ($pageTitle == 'Home') ? 'active':''; ?>">
            Home
        </li>
        </a>
        <a href="../overzicht.php">
        <li class="<?= ($pageTitle == 'Overzicht') ? 'active':''; ?>">
            Overzicht
        </li>
        </a>
        <a href="#">
        <li class="<?= ($pageTitle == 'Verleden') ? 'active':''; ?>">
            Verleden
        </li>
        </a>
    </ul>
</nav>
