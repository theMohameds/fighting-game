import org.common.services.ECSPlugin;


module Player {
    requires Common;

   // provides PlayerPlugin with org.common.services.ECSPlugin;
    provides org.common.services.ECSPlugin with plugins.PlayerPlugin; // Correct syntax


}
