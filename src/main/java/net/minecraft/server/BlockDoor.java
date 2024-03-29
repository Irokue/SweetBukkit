package net.minecraft.server;

import java.util.Random;

import org.bukkit.event.block.BlockRedstoneEvent; // CraftBukkit

public class BlockDoor extends Block {

    protected BlockDoor(int i, Material material) {
        super(i, material);
        this.textureId = 97;
        if (material == Material.ORE) {
            ++this.textureId;
        }

        float f = 0.5F;
        float f1 = 1.0F;

        this.a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
    }

    public boolean a() {
        return false;
    }

    public boolean b(IBlockAccess iblockaccess, int i, int j, int k) {
        int l = this.e(iblockaccess, i, j, k);

        return (l & 4) != 0;
    }

    public boolean b() {
        return false;
    }

    public int c() {
        return 7;
    }

    public AxisAlignedBB e(World world, int i, int j, int k) {
        this.updateShape(world, i, j, k);
        return super.e(world, i, j, k);
    }

    public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
        this.d(this.e(iblockaccess, i, j, k));
    }

    public int c(IBlockAccess iblockaccess, int i, int j, int k) {
        return this.e(iblockaccess, i, j, k) & 3;
    }

    public boolean d(IBlockAccess iblockaccess, int i, int j, int k) {
        return (this.e(iblockaccess, i, j, k) & 4) != 0;
    }

    private void d(int i) {
        float f = 0.1875F;

        this.a(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
        int j = i & 3;
        boolean flag = (i & 4) != 0;
        boolean flag1 = (i & 16) != 0;

        if (j == 0) {
            if (!flag) {
                this.a(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
            } else if (!flag1) {
                this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
            } else {
                this.a(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
            }
        } else if (j == 1) {
            if (!flag) {
                this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
            } else if (!flag1) {
                this.a(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            } else {
                this.a(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
            }
        } else if (j == 2) {
            if (!flag) {
                this.a(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            } else if (!flag1) {
                this.a(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
            } else {
                this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
            }
        } else if (j == 3) {
            if (!flag) {
                this.a(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
            } else if (!flag1) {
                this.a(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
            } else {
                this.a(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }
        }
    }

    public void attack(World world, int i, int j, int k, EntityHuman entityhuman) {
        this.interact(world, i, j, k, entityhuman);
    }

    public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman) {
        if (this.material == Material.ORE) {
            return true;
        } else {
            int l = this.e((IBlockAccess) world, i, j, k);
            int i1 = l & 7;

            i1 ^= 4;
            if ((l & 8) != 0) {
                world.setData(i, j - 1, k, i1);
                world.b(i, j - 1, k, i, j, k);
            } else {
                world.setData(i, j, k, i1);
                world.b(i, j, k, i, j, k);
            }

            world.a(entityhuman, 1003, i, j, k, 0);
            return true;
        }
    }

    public void setDoor(World world, int i, int j, int k, boolean flag) {
        int l = this.e((IBlockAccess) world, i, j, k);
        boolean flag1 = (l & 4) != 0;

        if (flag1 != flag) {
            int i1 = l & 7;

            i1 ^= 4;
            if ((l & 8) != 0) {
                world.setData(i, j - 1, k, i1);
                world.b(i, j - 1, k, i, j, k);
            } else {
                world.setData(i, j, k, i1);
                world.b(i, j, k, i, j, k);
            }

            world.a((EntityHuman) null, 1003, i, j, k, 0);
        }
    }

    public void doPhysics(World world, int i, int j, int k, int l) {
        int i1 = world.getData(i, j, k);

        if ((i1 & 8) != 0) {
            if (world.getTypeId(i, j - 1, k) != this.id) {
                world.setTypeId(i, j, k, 0);
            }
            else if (l > 0 && l != this.id) { // CraftBukkit
                this.doPhysics(world, i, j - 1, k, l);
            }
        } else {
            boolean flag = false;

            if (world.getTypeId(i, j + 1, k) != this.id) {
                world.setTypeId(i, j, k, 0);
                //flag = true; // CraftBukkit
            }

            if (!world.e(i, j - 1, k)) {
                world.setTypeId(i, j, k, 0);
                flag = true;
                if (world.getTypeId(i, j + 1, k) == this.id) {
                    world.setTypeId(i, j + 1, k, 0);
                }
            }

            if (flag) {
                if (!world.isStatic) {
                    this.b(world, i, j, k, i1, 0);
                }
            } else if (l > 0 && Block.byId[l].isPowerSource()) {
                org.bukkit.World bworld = world.getWorld();
                org.bukkit.block.Block block = bworld.getBlockAt(i, j, k);
                org.bukkit.block.Block blockTop = bworld.getBlockAt(i, j + 1, k);

                int power = block.getBlockPower();
                int powerTop = blockTop.getBlockPower();
                if (powerTop > power) power = powerTop;
                int oldPower = (world.getData(i, j, k) & 4) > 0 ? 15 : 0;

                if (oldPower == 0 ^ power == 0) {
                    BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, oldPower, power);
                    world.getServer().getPluginManager().callEvent(eventRedstone);

                    this.setDoor(world, i, j, k, eventRedstone.getNewCurrent() > 0);
                }
                // CraftBukkit end
            }
        }
    }

    public int getDropType(int i, Random random, int j) {
        return /* (i & 8) != 0 ? 0 CraftBukkit : */ (this.material == Material.ORE ? Item.IRON_DOOR.id : Item.WOOD_DOOR.id);
    }

    public MovingObjectPosition a(World world, int i, int j, int k, Vec3D vec3d, Vec3D vec3d1) {
        this.updateShape(world, i, j, k);
        return super.a(world, i, j, k, vec3d, vec3d1);
    }

    public boolean canPlace(World world, int i, int j, int k) {
        return j >= 255 ? false : world.e(i, j - 1, k) && super.canPlace(world, i, j, k) && super.canPlace(world, i, j + 1, k);
    }

    public int g() {
        return 1;
    }

    public int e(IBlockAccess iblockaccess, int i, int j, int k) {
        int l = iblockaccess.getData(i, j, k);
        boolean flag = (l & 8) != 0;
        int i1;
        int j1;

        if (flag) {
            i1 = iblockaccess.getData(i, j - 1, k);
            j1 = l;
        } else {
            i1 = l;
            j1 = iblockaccess.getData(i, j + 1, k);
        }

        boolean flag1 = (j1 & 1) != 0;
        int k1 = i1 & 7 | (flag ? 8 : 0) | (flag1 ? 16 : 0);

        return k1;
    }
}
